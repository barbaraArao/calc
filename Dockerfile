version: "3.8"

services:
  app:
    build:
      context: .
      dockerfile: src/main/docker/Dockerfile.jvm
    ports:
      - "8080:8080"
    depends_on:
      - redis

  redis:
    image: redis:7-alpine
    container_name: redis
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data

volumes:
  redis_data: