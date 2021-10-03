DOCKER_COMPOSE := docker-compose


.PHONY: dev
dev:
	test -f .env || (echo "Error: Please create .env file"; exit 1)
	$(DOCKER_COMPOSE) --file docker-compose.dev.yml up --build
