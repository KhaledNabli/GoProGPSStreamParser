import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Test {

	private static final String GPS_PATHNAME = "/Users/knabli/";
	private static final String GPX_PATHNAME = "/Users/knabli/";
	private static final String[] FileNames = { "GOPR0071", "GP010071", "GP020071", "GP030071", "GP040071", "GP050071" };
	//private static final String[] FileNames = { "GOPR0061", "GP010061", "GP020061", "GP030061" };

	public static void main(String[] args) throws IOException {

		dumpGPXFiles();
	}

	public static String[] analyzeStructure(String fileName) throws IOException {
		String structureResult[] = null;
		System.out.println("Analyzing File: " + GPS_PATHNAME + fileName + ".gps");
		Path path = Paths.get(GPS_PATHNAME + fileName + ".gps");

		ByteArrayInputStream fileRawContent = new ByteArrayInputStream(Files.readAllBytes(path));
		int i = 0;
		String blockSeq[] = new String[128];
		boolean fileSequenceCorrect = false;
		while (fileRawContent.available() > 0 && i < blockSeq.length) {

			DataBlock block = new DataBlock(fileRawContent);

			if (i < blockSeq.length) {
				blockSeq[i] = block.name;
			}
			if (block.name != null && block.name.equalsIgnoreCase("DEVC")) {
				System.out.println("==================================================");
				if (i > 0) {
					fileSequenceCorrect = true;
					break;
				}
			}

			System.out.println(i + ": " + block);
			i++;
		}
		if (fileSequenceCorrect) {
			structureResult = Arrays.copyOf(blockSeq, i);
			System.out.println("Block Count per Frame: " + i + " Blocks.");
			System.out.println("Block Sequence " + Arrays.toString(structureResult));
			System.out.println("Valid GPRP Structure.");
		} else {
			System.out.println("Could not find a valid structure... File seems to be corrupt?");
			System.out.println("Block Sequence " + Arrays.toString(blockSeq));
			structureResult = new String[0];
		}

		fileRawContent.close();

		return structureResult;

	}

	public static void dumpGPXFiles() throws IOException {

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		BufferedWriter gpxWriter = new BufferedWriter(new FileWriter(GPX_PATHNAME + FileNames[0] + ".gpx"));
		BufferedWriter gyroWriter = new BufferedWriter(new FileWriter(GPX_PATHNAME + FileNames[0] + ".gyro.csv"));
		BufferedWriter acclWriter = new BufferedWriter(new FileWriter(GPX_PATHNAME + FileNames[0] + ".accl.csv"));
		gpxWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\r\n<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" creator=\"Geovative Solutions GeoTours\" version=\"1.1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">");

		gpxWriter.write("<trk><name>" + FileNames[0] + "</name><number>" + 1 + "</number><trkseg>");
		
		for (int i = 0; i < FileNames.length; i++) {
			System.out.println("Analying File: " + GPS_PATHNAME + FileNames[i] + ".gps");

			Path path = Paths.get(GPS_PATHNAME + FileNames[i] + ".gps");

			//gpxWriter.write("<trk><name>" + FileNames[i] + "</name><number>" + i + "</number><trkseg>");
			ByteArrayInputStream fileRawContent = new ByteArrayInputStream(Files.readAllBytes(path));

			String[] fileStructure = analyzeStructure(FileNames[i]);

			while (fileRawContent.available() > 0) {
				DataFrame frame = new DataFrame(fileStructure, fileRawContent);
				Calendar frameTimestamp = frame.getGpsDate();

				/*
				 * DUMP GPS
				 */
				for (int k = 0; k < frame.gps.getNormalizedData().size(); k = k + 18) {
					gpxWriter.write(frame.gps.getNormalizedData().get(k).getTrackPoint());
				}

				
				/*
				 * DUMP GYRO
				 */
				int sampleRateGyro = 40;
				frameTimestamp = frame.getGpsDate();
				for (int j = 0; j < frame.gyro.getRawData().size(); j = j + 3 * sampleRateGyro) {
					short dataZ = frame.gyro.getRawData().get(j);
					short dataX = frame.gyro.getRawData().get(j + 1);
					short dataY = frame.gyro.getRawData().get(j + 2);

					gyroWriter.write(dateFormatter.format(frameTimestamp.getTime()) + ";" + frame.gyro.getScale() + ";"
							+ dataZ + ";" + dataX + ";" + dataY + ";\r\n");
					frameTimestamp.add(Calendar.MILLISECOND, 1000 / frame.gyro.getNormalizedData().size() * sampleRateGyro);
				}

				/*
				 * DUMP ACCL
				 */
				frameTimestamp = frame.getGpsDate();
				int sampleRateAccl = 20;
				for (int t = 0; t < frame.accl.getRawData().size(); t = t + 3 * sampleRateAccl) {
					short dataUD = frame.accl.getRawData().get(t);
					short dataRL = frame.accl.getRawData().get(t + 1);
					short dataFB = frame.accl.getRawData().get(t + 2);

					acclWriter.write(dateFormatter.format(frameTimestamp.getTime()) + ";" + frame.accl.getScale() + ";"
							+ dataUD + ";" + dataRL + ";" + dataFB + ";\r\n");
					frameTimestamp.add(Calendar.MILLISECOND, 1000 / frame.accl.getNormalizedData().size() * sampleRateAccl);
				}

			}
//			gpxWriter.write("</trkseg></trk>");
		}
		gpxWriter.write("</trkseg></trk>");
		gpxWriter.write("</gpx>");

		gpxWriter.close();
		gyroWriter.close();
		acclWriter.close();
	}

}
