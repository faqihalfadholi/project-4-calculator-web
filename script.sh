docker build -t faqihfadholi/calculator-web:latest .

docker volume create calculator-data

docker run -d --name calculator-container -p 8089:8080 -v calculator-data:/app/data faqihfadholi/calculator-web:latest

docker ps

docker logs calculator-container

docker stop calculator-container

docker start calculator-container

docker login -u faqihfadholi

docker push faqihfadholi/calculator-web:latest

docker pull faqihfadholi/calculator-web:latest

docker hub rm faqihfadholi/calculator-web:latest