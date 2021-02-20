import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:tas_face_detect/tas_face_detect.dart';

void main() {
  const MethodChannel channel = MethodChannel('tas_face_detect');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('findFaces', () async {
    expect(await TasFaceDetect.detectFace("image.jpg"), '2');
  });
}
