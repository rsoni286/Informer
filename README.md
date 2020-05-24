# Informer

Informer is a very easy to use library for displaying toast messages to the user. Informer is similar to snackbar, but displays messages at the top of the screen and provide three different animations.

### Add informer to your app

#### Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

#### Add the dependency

```
implementation 'com.github.rsoni286:Informer:1.0.0'
```

### Use it in your activity


#### Success
<img src="https://raw.githubusercontent.com/rsoni286/Informer/master/informer/ss3.jpg" alt="drawing" width="200"/>


#### Error
<img src="https://raw.githubusercontent.com/rsoni286/Informer/master/informer/ss2.jpg" alt="drawing" width="200"/>


#### Default
<img src="https://raw.githubusercontent.com/rsoni286/Informer/master/informer/ss1.jpg" alt="drawing" width="200"/>


#### Custom
<img src="https://raw.githubusercontent.com/rsoni286/Informer/master/informer/ss4.jpg" alt="drawing" width="200"/>

```
//for default view
Informer.informDefault(MainActivity.this, "This is default message");

//for success
Informer.informSuccess(MainActivity.this, "This is success message");

//for error
Informer.informError(MainActivity.this, "This is error message");

//for custom layout
 new Informer.WithOptions(MainActivity.this)
                        .setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.darker_gray))
                        .setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black))
                        .setAnimation(Informer.AnimType.SLIDE_LEFT_RIGHT)
                        .setDuration(Informer.Duration.LONG)
                        .inform("This is custom message");

```

