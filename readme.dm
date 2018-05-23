# Build
1)clean install
2)docker build -t netty-server . --force-rm=true
3)docker run -d -p 8080:8080 --name netty-server -t netty-server
