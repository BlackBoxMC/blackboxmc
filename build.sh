cd native

rustup target add x86_64-pc-windows-gnu
cargo build --release --target x86_64-pc-windows-gnu
cp ../native/target/x86_64-pc-windows-gnu/release/native.dll ../src/main/resources/native.dll
rustup target add x86_64-unknown-linux-gnu
cargo build --release --target x86_64-unknown-linux-gnu
cp ../native/target/x86_64-unknown-linux-gnu/release/libnative.so ../src/main/resources/libnative.so

cd .. 

./gradlew shadowJar
