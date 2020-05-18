Są trzy kontenery:
- zzpj-mysql
- zzpj-backend
- zzpj-frontend

Kontenery należą do tej samej sieci, a więc mogą się komunikować ze sobą z użyciem swoich nazw własnych.
Przykład: frontend chce zrobić GET na zasób "/users" w backendzie.

Źle: 
-  GET na "localhost:9090/users"

<b>Dobrze: 
-  GET na "zzpj-backend/users"</b>

<b>Wszystkie kontenery teraz budujemy i uruchamiamy skryptem bashowym poprzez komende:
./run.sh</b>


Manualnie:

Budujemy wszystkie kontenery:
-  docker-compose build

Uruchamiamy wszystkie kontenenery:
-  docker-compose up

Wyłączanie wszystkich kontenenerów:
-  docker-compose down

Uruchamianie/stopowanie wybranego konteneru:
-  docker-compose start zzpj-frontend
-  docker-compose stop zzpj-frontend

Lista kontenerów:
-  docker-compose ps
