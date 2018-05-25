# Build
1)clean install
2)docker build -t netty-server . --force-rm=true
3)docker run -d -p 8080:8080 --name netty-server -t netty-server

# yandex tank run
docker run -v /Users/askael/IdeaProjects/netty-server/yandexTank/:/var/loadtest -v $SSH_AUTH_SOCK:/ssh-agent -e SSH_AUTH_SOCK=/ssh-agent --net host -it direvius/yandex-tank