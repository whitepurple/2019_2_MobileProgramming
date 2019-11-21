package pics;

import java.util.HashMap;
import java.util.Map;

import javaxt.io.Image;

public class MPImage {
	private Image image;
	private HashMap<Integer, Object> exifTags;
	private HashMap<Integer, Object> gpsTags;
	private static final Map<Integer, String> dateTagName = new HashMap<>(
			Map.of(	 306,	"DateTime",				//Date and time of image creation.
					 36867,	"DateTimeOriginal",		//The date and time when the original image data was generated.
					 36868,	"DateTimeDigitized",		//The date and time when the image was stored as digital data.
					 37520,	"SubsecTime",			//A tag used to record fractions of seconds for the DateTime tag.
					 37521,	"SubsecTimeOriginal",	//A tag used to record fractions of seconds for the DateTimeOriginal tag.
					 37522,	"SubsecTimeDigitized"));	//A tag used to record fractions of seconds for the DateTimeDigitized tag.
	
	private static final Map<Integer, String> gpsTagName =
			Map.ofEntries(	Map.entry( 0,	"GPSVersionID"			),	//Indicates the version of GPSInfoIFD.
							Map.entry( 1,	"GPSLatitudeRef"		),	//Indicates whether the latitude is north or south latitude.
							Map.entry( 2,	"GPSLatitude"			),	//Indicates the latitude.
							Map.entry( 3,	"GPSLongitudeRef"		),	//Indicates whether the longitude is east or west longitude.
							Map.entry( 4,	"GPSLongitude"			),	//Indicates the longitude.
							Map.entry( 5,	"GPSAltitudeRef"		),	//Indicates the altitude used as the reference altitude.
							Map.entry( 6,	"GPSAltitude"			),	//Indicates the altitude based on the reference in GPSAltitudeRef.
							Map.entry( 7,	"GPSTimeStamp"			),	//Indicates the time as UTC (Coordinated Universal Time).
							Map.entry( 8,	"GPSSatellites"			),	//Indicates the GPS satellites used for measurements.
							Map.entry( 9,	"GPSStatus"				),	//Indicates the status of the GPS receiver when the image is recorded.
							Map.entry( 10,	"GPSMeasureMode"		),	//Indicates the GPS measurement mode.
							Map.entry( 11,	"GPSDOP"				),	//Indicates the GPS DOP (data degree of precision).
							Map.entry( 12,	"GPSSpeedRef"			),	//Indicates the unit used to express the GPS receiver speed of movement.
							Map.entry( 13,	"GPSSpeed"				),	//Indicates the speed of GPS receiver movement.
							Map.entry( 14,	"GPSTrackRef"			),	//Indicates the reference for giving the direction of GPS receiver movement.
							Map.entry( 15,	"GPSTrack"				),	//Indicates the direction of GPS receiver movement.
							Map.entry( 16,	"GPSImgDirectionRef"	),	//Indicates the reference for giving the direction of the image when it is captured.
							Map.entry( 17,	"GPSImgDirection"		),	//Indicates the direction of the image when it was captured.
							Map.entry( 18,	"GPSMapDatum"			),	//Indicates the geodetic survey data used by the GPS receiver.
							Map.entry( 19,	"GPSDestLatitudeRef"	),	//Indicates whether the latitude of the destination point is north or south latitude.
							Map.entry( 20,	"GPSDestLatitude"		),	//Indicates the latitude of the destination point.
							Map.entry( 21,	"GPSDestLongitudeRef"	),	//Indicates whether the longitude of the destination point is east or west longitude.
							Map.entry( 22,	"GPSDestLongitude"		),	//Indicates the longitude of the destination point.
							Map.entry( 23,	"GPSDestBearingRef"		),	//Indicates the reference used for giving the bearing to the destination point.
							Map.entry( 24,	"GPSDestBearing"		),	//Indicates the bearing to the destination point.
							Map.entry( 25,	"GPSDestDistanceRef"	),	//Indicates the unit used to express the distance to the destination point.
							Map.entry( 26,	"GPSDestDistance"		),	//Indicates the distance to the destination point.
							Map.entry( 27,	"GPSProcessingMethod"	),	//A character string recording the name of the method used for location finding.
							Map.entry( 28,	"GPSAreaInformation"	),	//A character string recording the name of the GPS area.
							Map.entry( 29,	"GPSDateStamp"			),	//A character string recording date and time information relative to UTC (Coordinated Universal Time).
							Map.entry( 30,	"GPSDifferential"		));	//Indicates whether differential correction is applied to the GPS receiver.

	public MPImage(String path) {
		image = new Image(path);
		exifTags = image.getExifTags();
		gpsTags = image.getGpsTags();
	}
	public Image getImage() {
		return image;
	}
	public HashMap<String,Object> getExifTagsForDate() {
		HashMap<String,Object> retval = new HashMap<>();
		exifTags.entrySet()
				.stream()
				.filter(e->dateTagName.containsKey(e.getKey())
							||gpsTagName.containsKey(e.getKey()))
				.map(e -> Map.entry(dateTagName.containsKey(e.getKey())?
						dateTagName.get(e.getKey()):gpsTagName.get(e.getKey()),
						e.getValue()))
				.forEach(e->retval.put(e.getKey(),e.getValue()));
		if(gpsTags.containsKey(29)) {
			retval.put(gpsTagName.get(29), gpsTags.get(29));
		}
		return retval;
	}
}
