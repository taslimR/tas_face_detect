import 'dart:async';

import 'package:flutter/services.dart';

class TasFaceDetect {
  static const MethodChannel _channel = const MethodChannel('tas_face_detect');

  static Future<int> detectFace(String imagePath) async {
    final int count =
        await _channel.invokeMethod('findFaces', {"path": imagePath});
    return count;
  }
}
