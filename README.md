# AnswerQ

AnswerQ is a scalable and secure platform for **form and survey management**, built with modern Java technologies and production-ready observability tools.  
It provides user management, authentication, roles, survey creation, responses handling, auditing, and system monitoring — ready to run on Docker, orchestrate with Prometheus, and visualize with Grafana.

---

## 🛠️ Tech

![Java](https://img.shields.io/badge/Java-21%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring_Security-enabled-darkgreen)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-active-green)
![Spring Actuator](https://img.shields.io/badge/Spring_Actuator-monitoring-lightgrey)

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI_3-orange)
![Prometheus](https://img.shields.io/badge/Prometheus-metrics-orange)
![Grafana](https://img.shields.io/badge/Grafana-dashboards-yellow)
![Maven](https://img.shields.io/badge/Build-Maven-red)
![Docker](https://img.shields.io/badge/Docker-containers-blue)

---

## 🔐 Environment Variables (.env)

Configure your environment variables before running the project.
Create a .env file in the root directory with the folowing values:

### Application Settings
| Variable | Example Value | Description |
|---------|---------------|-------------|
| `PORT` | `8081` | Main application port |
| `SPRING_PROFILES_ACTIVE` | `dev` | Active Spring Boot profile |

### Keycloak Admin Credentials
| Variable | Example Value | Description |
|---------|---------------|-------------|
| `KEYCLOAK_ADMIN_ENV` | `admin` | Keycloak admin username |
| `KEYCLOAK_ADMIN_PASSWORD_ENV` | `admin123` | Keycloak admin password |

### Database Configuration
| Variable | Example Value | Description |
|---------|---------------|-------------|
| `ANSWERQ_DB` | `answerq` | Database name |
| `ANSWERQ_DB_USERNAME` | `answerq_user` | Database username |
| `ANSWERQ_DB_PASSWORD` | `strongpassword123` | Database password |
| `ANSWERQ_DB_HOST` | `answerq-db` | Database hostname |
| `ANSWERQ_DB_PORT` | `5432` | PostgreSQL port |

### Security / JWT
| Variable | Example Value | Description |
|---------|---------------|-------------|
| `RESOURCE_SERVER_ISSUER_URI` | `http://localhost:8080/realms/answerq-realm` | Keycloak issuer URI |
| `RESOURCE_SERVER_JWK_URI` | `http://keycloak:8080/realms/answerq-realm/protocol/openid-connect/certs` | JWK endpoint |
| `JWT_SECRET_KEY` | `mysupersecretjwtkeyexample1234567890` | JWT secret key |
| `PBKDF2_PEPPER` | `pepp3r-EXAMPLE-KEY-1234567890abcd` | Pepper for password hashing |

### Email Configuration
| Variable | Example Value | Description |
|---------|---------------|-------------|
| `EMAIL_USERNAME` | `example@email.com` | Email sender |
| `EMAIL_PASSWORD` | `emailpassword123` | Email sender password |

### Grafana Admin
| Variable | Example Value | Description |
|---------|---------------|-------------|
| `GF_SECURITY_ADMIN_USER` | `admin` | Grafana admin |
| `GF_SECURITY_ADMIN_PASSWORD` | `admin123` | Grafana admin password |

### Actuator Monitoring
| Variable | Example Value | Description |
|---------|---------------|-------------|
| `ACTUATOR_PORT` | `8082` | Actuator port |
| `ACTUATOR_ADDRESS` | `0.0.0.0` | Bind address |
| `ACTUATOR_USERNAME` | `monitor` | Actuator user |
| `ACTUATOR_PASSWORD` | `monitor123` | Actuator password |

---
