package com.taslim.tas_face_detect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** TasFaceDetectPlugin */
public class TasFaceDetectPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "tas_face_detect");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("findFaces")) {
      String path = call.argument("path");
      System.out.println(path);
      final int[] faceCount = {0};
      try {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        FaceDetector detector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 10);
//        FaceDetector.Face[] faces = new FaceDetector.Face[10];
//        faceCount = detector.findFaces(bitmap, faces);
        FaceDetectorOptions options = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .build();
        FaceDetector detector =  FaceDetection.getClient(options);
        Task<List<Face>> resultFace =
                detector.process(InputImage.fromBitmap(bitmap, 0))
                        .addOnSuccessListener(
                                new OnSuccessListener<List<Face>>() {
                                  @Override
                                  public void onSuccess(List<Face> faces) {
                                    // Task completed successfully
                                    // ...
//                                    Toast.makeText(null, "Face count : " + faces.size(), Toast.LENGTH_LONG);
                                    faceCount[0] = faces.size();
                                  }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                    // Task failed with an exception
                                    // ...
                                  }
                                });
//        faceCount = detector.process(InputImage.fromBitmap(bitmap, 0)).;
//        faceCount = resultFace.getResult().size();
      } catch (Exception e) {
        e.printStackTrace();
      }
      result.success(faceCount[0]);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
