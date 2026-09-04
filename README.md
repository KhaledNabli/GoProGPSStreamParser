# GoPro GPS Stream Parser

Experimental Java parser for GPS, gyroscope, and accelerometer records extracted from GoPro metadata streams.

The parser reads `.gps` sidecar files, identifies GPMF-style data blocks, and writes:

- a GPX track;
- sampled gyroscope data as CSV;
- sampled accelerometer data as CSV.

## Build

The sources use the default Java package and have no external dependencies:

```bash
mkdir -p out
javac -d out src/*.java
```

## Run

Edit `GPS_PATHNAME`, `GPX_PATHNAME`, and `FileNames` in `src/Test.java`, then run:

```bash
java -cp out Test
```

The input is expected to be an already-extracted `.gps` stream, not an MP4 file.

## Status

This is a 2017 research prototype with hard-coded paths, no command-line interface, no test fixtures, and no validation against current GoPro telemetry formats. Use it as reference code and verify all generated data independently.
