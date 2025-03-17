.PHONY: redis-up redis-down redis-logs redis-cli

REDIS_CONTAINER_NAME=local-redis
REDIS_PORT=6379

redis-up:
	docker run -d --name $(REDIS_CONTAINER_NAME) -p $(REDIS_PORT):6379 redis:7-alpine

redis-down:
	docker rm -f $(REDIS_CONTAINER_NAME)

redis-logs:
	docker logs -f $(REDIS_CONTAINER_NAME)

redis-cli:
	docker exec -it $(REDIS_CONTAINER_NAME) redis-cli