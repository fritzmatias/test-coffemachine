#! /bin/bash
# Simple builder 

build(){
(cd coffemachine; mvn package) 
docker-compose build --no-cache --force-rm
}

setupData(){
  curl --header "Content-Type: application/json" \
  --request POST \
  --data '[
  {
    "name": "Coffe with milk",
    "price": 5,
    "description": "A large phone with one of the best screens"
  },
  {
    "name": "Cocoa",
    "price": 4,
    "description": "A great phone with one of the best cameras"
  },
  {
    "name": "Sugar",
    "price": 1,
    "description": ""
  }
]' \
  http://localhost:8080/api/v1/setupProducts  >/dev/null

  curl --header "Content-Type: application/json" \
  --request POST \
  --data '
{
  "productId" : 1,
  "associations" : [ 2, 3 ]
} 
' \
  http://localhost:8080/api/v1/productAssociations >/dev/null

  curl --header "Content-Type: application/json" \
  --request POST \
  --data '
{
  "productId" : 2,
  "associations" : [ 1, 3 ]
} 
' \
  http://localhost:8080/api/v1/productAssociations >/dev/null

}

case $1 in
	build)
		build
		;;	
	setup)
		setupData
		;;
	stop)
		docker-compose down
		;;
	run)
		docker-compose up
		;;
	*)
		echo "Check your options"
esac
