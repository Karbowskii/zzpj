Są 3 kontenery:
-zzpj-mysql
-zzpj-backend
-zzpj-frontend

Kontenery należą do tej samej sieci, a więc mogą się komunikować między sobą z użyciem swoich nazw.
Przykład: frontend chce zrobić geta na zasób /users w backendzie.
Źle: GET "localhost:9090/users"
Dobrze: GET "zzpj-backend/users"

Budujemy wszystkie kontenery:
docker-compose build

Uruchamiamy wszystkie kontenenery:
docker-compose up

Wyłączanie wszystkich kontenenerów:
docker-compose down

Uruchamianie/stopowanie wybranego konteneru:
np. dla zzpj-frontend
docker-compose start zzpj-frontend
docker-compose stop zzpj-frontend

Lista kontenerów:
docker-compose ps
