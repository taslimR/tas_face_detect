package com.taslim.tas_face_detect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;

import androidx.annotation.NonNull;


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
      int faceCount = 0;
      try {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        FaceDetector detector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 10);
        FaceDetector.Face[] faces = new FaceDetector.Face[10];
        faceCount = detector.findFaces(bitmap, faces);
      } catch (Exception e) {
        e.printStackTrace();
      }
      result.success(faceCount);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
