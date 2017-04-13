# manishkprpickersdk
ImagePicker using camera and gallery

https://jitpack.io/#manishkpr/manishkprpickersdk/

[![](https://jitpack.io/v/manishkpr/manishkprpickersdk.svg)](https://jitpack.io/#manishkpr/manishkprpickersdk)

# Step 1. Add the JitPack repository to your build file

allprojects 
{
		
	repositories {
		...
		maven { url 'https://jitpack.io' }
		maven { url "https://repo.commonsware.com.s3.amazonaws.com" }
	        maven { url "https://s3.amazonaws.com/repo.commonsware.com" }
	}
		
}
  
# Step 2. Add the dependency
  
	dependencies {
		compile 'com.github.manishkpr:manishkprpickersdk:1.0'
	}
	
