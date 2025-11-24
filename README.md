# AnswerQ

AnswerQ is a scalable and secure platform for **form and survey management**, built with modern Java technologies and production-ready observability tools.  
It provides user management, authentication, roles, survey creation, responses handling, auditing, and system monitoring — ready to run on Docker, orchestrate with Prometheus, and visualize with Grafana.

---

## 🛠️ Tech

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.0-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring_Security-3.5.0-darkgreen)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-3.5.0-green)
![Spring Actuator](https://img.shields.io/badge/Spring_Actuator-3.5.0-lightgrey)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-driver-blue)
![SpringDoc OpenAPI](https://img.shields.io/badge/OpenAPI-2.8.12-orange)
![JJWT](https://img.shields.io/badge/JJWT-0.12.6-red)
![Maven](https://img.shields.io/badge/Maven-Build-red)

![Micrometer Prometheus](https://img.shields.io/badge/Micrometer_Prometheus-latest-orange)
![Docker](https://img.shields.io/badge/Docker-Container-blue)

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
