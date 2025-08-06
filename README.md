# Yoda - Content Management System

[![Build and Deploy](https://github.com/your-username/yoda/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/your-username/yoda/actions/workflows/ci-cd.yml)

A modern Content Management System (CMS) built with Spring Boot, featuring a comprehensive admin panel, user management, content creation, and real-time messaging capabilities.

## 🚀 Features

- **Content Management**: Create, edit, and manage articles, brands, and categories
- **User Management**: Registration, authentication, and role-based access control
- **Real-time Messaging**: RocketMQ integration for asynchronous communication
- **Search & Discovery**: Elasticsearch-powered content search
- **Responsive Design**: Bootstrap-based responsive UI
- **Multi-environment Support**: Local development and Docker deployment
- **CI/CD Pipeline**: Automated build and deployment to AliCloud

## 🏗️ Architecture

### Technology Stack

- **Backend**: Spring Boot 3.5.2, Java 21
- **Database**: MySQL 8.0
- **Cache**: Redis
- **Message Queue**: Apache RocketMQ
- **Search**: Elasticsearch (optional)
- **Frontend**: Thymeleaf, Bootstrap 3.2.0
- **Security**: Spring Security
- **ORM**: MyBatis Plus
- **Container**: Docker, Docker Compose

### System Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Backend       │    │   Infrastructure │
│   (Thymeleaf)   │◄──►│   (Spring Boot) │◄──►│   (Docker)      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                              │                        │
                              ▼                        ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │   Data Layer    │    │   Services      │
                       │   MySQL + Redis │    │   RocketMQ      │
                       └─────────────────┘    └─────────────────┘
```

## 📋 Prerequisites

- **Java**: JDK 21 (OpenJDK or Eclipse Temurin)
- **Gradle**: 8.0+ (or use Gradle Wrapper)
- **Docker**: 20.10+ (for containerized deployment)
- **Docker Compose**: 2.0+
- **MySQL**: 8.0+
- **Redis**: 6.0+

## 🛠️ Local Development Setup

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/yoda.git
cd yoda
```

### 2. Database Setup

Create a MySQL database and update the configuration:

```sql
CREATE DATABASE yoda CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Environment Configuration

Copy and configure the application properties:

```bash
cp src/main/resources/application-local.yml src/main/resources/application-local.yml.backup
```

Update `src/main/resources/application-local.yml` with your database credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/yoda?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
    password: # your_redis_password
```

### 4. Build and Run

```bash
# Build the project
./gradlew clean build

# Run locally
./gradlew bootRun
```

The application will be available at `http://localhost:8080`

## 🐳 Docker Deployment

### 1. Prerequisites

Ensure you have the following services running:
- MySQL on host (port 3306)
- Redis on host (port 6379)
- RocketMQ in Docker (see RocketMQ setup below)

### 2. Create Shared Network

```bash
docker network create taklip-shared-network
```

### 3. Build and Run with Docker Compose

```bash
# Build the Docker image
docker build -t yoda:latest .

# Start the application
docker compose up -d
```

### 4. RocketMQ Setup (Optional)

If you need RocketMQ for messaging features:

```yaml
# rocketmq-compose.yml
services:
  namesrv:
    image: apache/rocketmq:5.1.4
    container_name: rmq-namesrv
    ports:
      - 9876:9876
    networks:
      - taklip-shared-network
    command: sh mqnamesrv

  broker:
    image: apache/rocketmq:5.1.4
    container_name: rmq-broker
    depends_on:
      - namesrv
    ports:
      - 10911:10911
      - 10909:10909
    networks:
      - taklip-shared-network
    environment:
      NAMESRV_ADDR: "namesrv:9876"
    command: sh mqbroker -n namesrv:9876

networks:
  taklip-shared-network:
    external: true
```

## 🔧 Configuration

### Application Profiles

- **local**: Development environment
- **docker**: Containerized deployment
- **prod**: Production environment

### Key Environment Variables

```yaml
# Database
SPRING_DATASOURCE_URL: jdbc:mysql://host:3306/yoda
SPRING_DATASOURCE_USERNAME: root
SPRING_DATASOURCE_PASSWORD: password

# Redis
SPRING_REDIS_HOST: localhost
SPRING_REDIS_PORT: 6379
SPRING_REDIS_PASSWORD: password

# RocketMQ
ROCKETMQ_NAME_SERVER: namesrv:9876

# JVM Options
JAVA_OPTS: -Xms512m -Xmx1024m -XX:+UseG1GC
```

## 🚀 CI/CD Pipeline

The project includes a GitHub Actions workflow for automated deployment:

### Workflow Features

- **Automated Build**: Triggers on push to master branch
- **Docker Image**: Builds and pushes to AliCloud ACR
- **ECS Deployment**: Deploys to AliCloud ECS via SSH
- **Health Checks**: Verifies application startup
- **Rollback Support**: Handles deployment failures

### Required Secrets

Configure these secrets in your GitHub repository:

- `ALIYUN_ACR_REGISTRY`: AliCloud Container Registry URL
- `ALIYUN_ACR_USERNAME`: ACR username
- `ALIYUN_ACR_PASSWORD`: ACR password
- `ALIYUN_ECS_HOST`: ECS server IP
- `ALIYUN_ECS_USER`: ECS username
- `ALIYUN_ECS_SSH_KEY`: SSH private key for ECS access

## 📁 Project Structure

```
yoda/
├── src/
│   ├── main/
│   │   ├── java/com/taklip/yoda/
│   │   │   ├── api/           # REST API controllers
│   │   │   ├── controller/     # Web controllers
│   │   │   ├── service/        # Business logic
│   │   │   ├── model/          # Entity classes
│   │   │   ├── mapper/         # MyBatis mappers
│   │   │   └── config/         # Configuration classes
│   │   ├── resources/
│   │   │   ├── templates/      # Thymeleaf templates
│   │   │   ├── static/         # Static assets
│   │   │   └── mapper/         # MyBatis XML files
│   │   └── application-*.yml   # Environment configs
├── compose.yaml                 # Docker Compose
├── Dockerfile                   # Docker image
├── build.gradle                 # Gradle build
└── .github/workflows/           # CI/CD workflows
```

## 🔍 API Endpoints

### Health Check
- `GET /actuator/health` - Application health status

### Content Management
- `GET /content` - List all content
- `POST /content` - Create new content
- `PUT /content/{id}` - Update content
- `DELETE /content/{id}` - Delete content

### User Management
- `GET /user/profile` - Get user profile
- `POST /user/register` - User registration
- `POST /user/login` - User authentication

## 🧪 Testing

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests UserServiceTest

# Run with coverage
./gradlew test jacocoTestReport
```

## 📊 Monitoring

The application includes Spring Boot Actuator for monitoring:

- Health checks: `/actuator/health`
- Metrics: `/actuator/metrics`
- Info: `/actuator/info`

## 🔒 Security

- Spring Security integration
- Role-based access control
- CSRF protection
- Password validation
- Session management

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Failed**
   - Verify MySQL is running on port 3306
   - Check database credentials in application.yml
   - Ensure database `yoda` exists

2. **Redis Connection Failed**
   - Verify Redis is running on port 6379
   - Check Redis configuration in application.yml

3. **Docker Container Issues**
   - Check container logs: `docker logs yoda-app`
   - Verify network connectivity: `docker network ls`
   - Ensure shared network exists: `docker network create taklip-shared-network`

4. **CI/CD Pipeline Failures**
   - Verify GitHub secrets are configured
   - Check ECS server accessibility
   - Review deployment logs in GitHub Actions

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Apache RocketMQ for messaging capabilities
- Bootstrap team for the responsive UI framework
- AliCloud for cloud infrastructure

---

**Yoda** - May the content be with you! 🚀