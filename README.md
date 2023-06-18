# BlackBox

BlackBox is a Minecraft plugin that will allow you to write plugins in many other compiled languages besides Java. It does what you could do with JNI, but it handles all the Minecraft stuff for you automatically, so you don't even have to use Java's tooling ever again. Because JNI can be very unruley to work with, its developed alongside a [Rust library](https://github.com/BlackBoxMC/blackbox-rs/), and a C++ library is planned, although it is low priority.

The repo is licensed under AGPL. I understand this may is a controversional, but I've put too much work into this project to risk somebody bringing it into closed source (and GPL is useless since this is a plugin). I'd like to make it clear that I don't care about your users knowing their rights, I don't care about GitHub forks or etc. violating the license, and I don't think a lot of the loopholes against the AGPL apply here. All I care about is if you modify the plugin, I'd like to see the source.

---

**This plugin is made knowing why this hasn't been done before and why you might not want to do it.** 

- Distributing plugins made this way becomes much harder.
- You technically lose performance since JNI calls are slow (...they take about 15ms on a good machine but this might matter in some scenarios)
- You lose the ability to link to, and therefore create addons for, other plugins, unless you want to fork this repo and add support for the plugin in question (I am considering adding support in the helper library for the most popular plugins). 

**It's assumed that you don't care.** This is for the people who are sick of Java, never want to work with Gradle or IntelliJ ever again, and just want to use a language that they actually like (assuming they dislike Java).
