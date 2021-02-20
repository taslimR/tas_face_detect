
import 'dart:async';

import 'package:flutter/services.dart';

class TasFaceDetect {
  static const MethodChannel _channel =
      const MethodChannel('tas_face_detect');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
