# tas_face_detect

A new flutter plugin to get number of faces in an image.

## Getting Started

This project is a starting point for a Flutter
[plug-in package](https://flutter.dev/developing-packages/),
a specialized package that includes platform-specific implementation code for
Android and/or iOS.

For help getting started with Flutter, view our
[online documentation](https://flutter.dev/docs), which offers tutorials,
samples, guidance on mobile development, and a full API reference.

Pass the image path in flutter channet as "path" param in platform channel.


Add below line in the dart class 

``static const platformMethodChannel = const MethodChannel('tas_face_detect');``

Use below line where you want to get the face count.
``await App.platformMethodChannel.invokeMethod('findFaces', {"path": imagePath});``