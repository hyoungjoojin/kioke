# Kioke Commons
## Package for common code among kioke services.

### Usage
- Prerequisites
1. Add the following two fields to "$PROJECT_DIRECTORY/.env".
```bash
GITHUB_USERNAME=hyoungjoojin
GITHUB_ACCESS_TOKEN=<GITHUB ACCESS TOKEN>
```

2. Run `direnv allow`.

- Manual Deployment
1. Run the following script at the project's root directory.
```bash
./mvnw -f commons/pom.xml -s ./settings.xml deploy
```
