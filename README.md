Nox [![Build Status](https://travis-ci.org/pedrovgs/Nox.svg?branch=master)](https://travis-ci.org/pedrovgs/Nox) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Nox-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1764) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pedrovgs/nox/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pedrovgs/nox)
===

Nox is an Android library created to show a custom view with some images or drawables ([NoxItem][1] instances) inside which are drawn following a shape indicated by the library user. You can create you own [Shape][2] implementations if you want, by default there are some interesting Shape implementations to show NoxItem instances following a circular, spiral or linear shape. The space needed to show you NoxItem instances will be calculated automatically by the library and the scroll effect will be enabled if needed. If the Shape you choose needs a bidirectional scroll, like [CircularShape][3], this will be enabled automatically.

Screenshots
-----------
![Demo Screenshot][4]

Usage
-----

To use Nox inside your layouts you have to follow this steps:

* **1** - Add a [NoxView][5] widget to your layout:

```xml

  <com.github.pedrovgs.nox.NoxView
      xmlns:nox="http://schemas.android.com/apk/res-auto"
      android:id="@+id/nox_view"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      nox:item_size="@dimen/nox_item_size"
      nox:item_margin="@dimen/nox_item_margin"
      nox:item_placeholder="@drawable/nox_item_placeholder"
      nox:shape="circular_shape"/>

```

* **2** - Provide different configurations based on styleable attributes:

* NoxItem size: ``nox:item_size``
* NoxItem margin: ``nox:item_margin``
* NoxItem placeholder: ``nox:item_placeholder``. This placeholder will be used if the NoxItem to draw has no a placeholder configured.
* Use circular transformation: ``nox:use_circular_transformation``. Applies a Picasso circular transformation to the resource loaded.
* Shape used: ``nox:shape``. Review [attrs.xml][6] file to know the list of shapes ready to be used in the library.

**This configuration can be also provided programmatically. Take a look to the sample project to find some samples**.

* **3** - Create a List<NoxItem> and invoke the method **showNoxItems** in your NoxView instance:

```java

  List<NoxItem> noxItems = new ArrayList<NoxItem>();
  noxItems.add(new NoxItem("http://api.randomuser.me/portraits/thumb/1.jpg"));
  noxView.showNoxItems(noxItems);

```

If you've added a new NoxItem to your list and you want to redraw your NoxView you can use **notifyDataSetChanged** method:

```java

   noxView.notifyDataSetChanged();
   
```

If you are going to download any resource from internet remember to add the internet permission to your AndroidManifest.

**To be able to configure a custom Shape implementation review [MainActivity][11] class.**

Add it to your project
----------------------

Add Nox dependency to your build.gradle

```groovy

dependencies{
    compile 'com.github.pedrovgs:nox:1.0'
}

```

Or add Nox as a new dependency inside your pom.xml

```xml

<dependency>
    <groupId>com.github.pedrovgs</groupId>
    <artifactId>nox</artifactId>
    <version>1.0</version>
    <type>aar</type>
</dependency>

```

Do you want to contribute?
--------------------------

Please, do it!!! I'd like to improve this library with your help, there are some new features to implement waiting for you ;) Take a look to the repository issues.

Libraries used in this project
------------------------------
* [Robolectric] [7]
* [JUnit] [8]
* [Mockito] [9]
* [Picasso] [10]

Developed By
------------

* Pedro Vicente Gómez Sánchez - <pedrovicente.gomez@gmail.com>

<a href="https://twitter.com/pedro_g_s">
  <img alt="Follow me on Twitter" src="https://image.freepik.com/iconos-gratis/twitter-logo_318-40209.jpg" height="60" width="60"/>
</a>
<a href="https://es.linkedin.com/in/pedrovgs">
  <img alt="Add me to Linkedin" src="https://image.freepik.com/iconos-gratis/boton-del-logotipo-linkedin_318-84979.png" height="60" width="60"/>
</a>

License
-------

    Copyright 2015 Pedro Vicente Gómez Sánchez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[1]: https://github.com/pedrovgs/Nox/tree/master/nox/src/main/java/com/github/pedrovgs/nox/NoxItem.java
[2]: https://github.com/pedrovgs/Nox/tree/master/nox/src/main/java/com/github/pedrovgs/nox/shape/Shape.java
[3]: https://github.com/pedrovgs/Nox/tree/master/nox/src/main/java/com/github/pedrovgs/nox/shape/CircularShape.java
[4]: ./art/screenshot_demo_1.gif
[5]: https://github.com/pedrovgs/Nox/tree/master/nox/src/main/java/com/github/pedrovgs/nox/NoxView.java
[6]: https://github.com/pedrovgs/Nox/blob/master/nox/src/main/res/values/attrs.xml
[7]: https://github.com/robolectric/robolectric
[8]: https://github.com/junit-team/junit
[9]: https://github.com/mockito/mockito
[10]: https://github.com/square/picasso
[11]: https://github.com/pedrovgs/Nox/blob/master/sample/src/main/java/com/github/pedrovgs/nox/sample/MainActivity.java
