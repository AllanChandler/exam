## Github link

https://github.com/AllanChandler/exam

## 3.3.3

POST http://localhost:7070/api/trips/populate

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:44:35 GMT
Content-Type: application/json
Content-Length: 97

{
"status": 200,
"message": "Database populated successfully.",
"timestamp": "2024-11-04 10:44:35.247"
}
Response file saved.
> 2024-11-04T104435.200.json

Response code: 200 (OK); Time: 535ms (535 ms); Content length: 97 bytes (97 B)

POST http://localhost:7070/api/trips

HTTP/1.1 201 Created
Date: Mon, 04 Nov 2024 09:45:57 GMT
Content-Type: application/json
Content-Length: 183

{
"id": 6,
"name": "Beach Getaway",
"starttime": [
2024,
6,
1
],
"endtime": [
2024,
6,
10
],
"startposition": "Miami Beach",
"price": 1200.0,
"category": "BEACH",
"guideFirstname": null,
"guideLastname": null
}
Response file saved.
> 2024-11-04T104557.201.json

Response code: 201 (Created); Time: 66ms (66 ms); Content length: 183 bytes (183 B)

GET http://localhost:7070/api/trips

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:46:35 GMT
Content-Type: application/json
Content-Length: 1130

[
{
"id": 1,
"name": "Beach Getaway",
"starttime": [
2024,
6,
1
],
"endtime": [
2024,
6,
10
],
"startposition": "Miami Beach",
"price": 1200.0,
"category": "BEACH",
"guideFirstname": "Alice",
"guideLastname": "Johnson"
},
{
"id": 2,
"name": "Mountain Adventure",
"starttime": [
2024,
7,
15
],
"endtime": [
2024,
7,
20
],
"startposition": "Rocky Mountains",
"price": 800.0,
"category": "FOREST",
"guideFirstname": "Alice",
"guideLastname": "Johnson"
},
{
"id": 3,
"name": "City Tour",
"starttime": [
2024,
8,
1
],
"endtime": [
2024,
8,
5
],
"startposition": "New York City",
"price": 1500.0,
"category": "CITY",
"guideFirstname": "Bob",
"guideLastname": "Smith"
},
{
"id": 4,
"name": "Cultural Journey",
"starttime": [
2024,
9,
10
],
"endtime": [
2024,
9,
15
],
"startposition": "Tokyo",
"price": 2000.0,
"category": "CITY",
"guideFirstname": "Bob",
"guideLastname": "Smith"
},
{
"id": 5,
"name": "Desert Safari",
"starttime": [
2024,
10,
5
],
"endtime": [
2024,
10,
10
],
"startposition": "Dubai",
"price": 1800.0,
"category": "SEA",
"guideFirstname": "Clara",
"guideLastname": "Lee"
},
{
"id": 6,
"name": "Beach Getaway",
"starttime": [
2024,
6,
1
],
"endtime": [
2024,
6,
10
],
"startposition": "Miami Beach",
"price": 1200.0,
"category": "BEACH",
"guideFirstname": null,
"guideLastname": null
}
]
Response file saved.
> 2024-11-04T104635.200.json

Response code: 200 (OK); Time: 109ms (109 ms); Content length: 1130 bytes (1,13 kB)

PUT http://localhost:7070/api/trips/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:48:42 GMT
Content-Type: application/json
Content-Length: 199

{
"id": 1,
"name": "Updated Beach Getaway",
"starttime": [
2024,
6,
1
],
"endtime": [
2024,
6,
12
],
"startposition": "Miami Beach",
"price": 1300.0,
"category": "BEACH",
"guideFirstname": "Alice",
"guideLastname": "Johnson"
}
Response file saved.
> 2024-11-04T104842.200.json

Response code: 200 (OK); Time: 32ms (32 ms); Content length: 199 bytes (199 B)

DELETE http://localhost:7070/api/trips/1

HTTP/1.1 204 No Content
Date: Mon, 04 Nov 2024 09:48:58 GMT
Content-Type: text/plain

<Response body is empty>;

Response code: 204 (No Content); Time: 17ms (17 ms); Content length: 0 bytes (0 B)

PUT http://localhost:7070/api/trips/6/guides/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:49:10 GMT
Content-Type: application/json
Content-Length: 98

{
"status": 200,
"message": "Guide successfully added to trip.",
"timestamp": "2024-11-04 10:49:10.877"
}
Response file saved.
> 2024-11-04T104910.200.json

Response code: 200 (OK); Time: 24ms (24 ms); Content length: 98 bytes (98 B)

GET http://localhost:7070/api/trips/6

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:49:24 GMT
Content-Type: application/json
Content-Length: 191

{
"id": 6,
"name": "Beach Getaway",
"starttime": [
2024,
6,
1
],
"endtime": [
2024,
6,
10
],
"startposition": "Miami Beach",
"price": 1200.0,
"category": "BEACH",
"guideFirstname": "Alice",
"guideLastname": "Johnson"
}
Response file saved.
> 2024-11-04T104924.200.json

Response code: 200 (OK); Time: 9ms (9 ms); Content length: 191 bytes (191 B)

GET http://localhost:7070/api/trips/guides/1

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:49:39 GMT
Content-Type: application/json
Content-Length: 395

[
{
"id": 2,
"name": "Mountain Adventure",
"starttime": [
2024,
7,
15
],
"endtime": [
2024,
7,
20
],
"startposition": "Rocky Mountains",
"price": 800.0,
"category": "FOREST",
"guideFirstname": "Alice",
"guideLastname": "Johnson"
},
{
"id": 6,
"name": "Beach Getaway",
"starttime": [
2024,
6,
1
],
"endtime": [
2024,
6,
10
],
"startposition": "Miami Beach",
"price": 1200.0,
"category": "BEACH",
"guideFirstname": "Alice",
"guideLastname": "Johnson"
}
]
Response file saved.
> 2024-11-04T104939.200.json

Response code: 200 (OK); Time: 12ms (12 ms); Content length: 395 bytes (395 B)

## 3.3.5

I use PUT because it updates something already existing which is the trip because I add a guide 
whereas if I did POST then I would be adding something but I need to update therefore I used PUT

## 5.2

New endpoint for filtered categories:

GET http://localhost:7070/api/trips/categories/BEACH

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 09:51:06 GMT
Content-Type: application/json
Content-Length: 193

[
{
"id": 6,
"name": "Beach Getaway",
"starttime": [
2024,
6,
1
],
"endtime": [
2024,
6,
10
],
"startposition": "Miami Beach",
"price": 1200.0,
"category": "BEACH",
"guideFirstname": "Alice",
"guideLastname": "Johnson"
}
]
Response file saved.
> 2024-11-04T105106.200.json

Response code: 200 (OK); Time: 636ms (636 ms); Content length: 193 bytes (193 B)

# Task 6

GET http://localhost:7070/api/trips/packingitems/beach

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:49:59 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 537

{
"items": [
{
"name": "Beach Umbrella",
"weightInGrams": 1200,
"quantity": 1,
"description": "Sunshade umbrella for beach outings.",
"category": "beach",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Sunny Store",
"shopUrl": "https://shop3.com",
"price": 50.0
},
{
"shopName": "Beach Essentials",
"shopUrl": "https://shop4.com",
"price": 55.0
}
]
},
{
"name": "Beach Water Bottle",
"weightInGrams": 500,
"quantity": 1,
"description": "High-capacity water bottle for hot climates.",
"category": "beach",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Hydration Depot",
"shopUrl": "https://shop6.com",
"price": 25.0
}
]
},
{
"name": "Beach Cooler",
"weightInGrams": 3000,
"quantity": 1,
"description": "Insulated cooler to keep beverages cold at the beach.",
"category": "beach",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Beach Supplies",
"shopUrl": "https://shop21.com",
"price": 70.0
}
]
},
{
"name": "Beach Towel",
"weightInGrams": 300,
"quantity": 1,
"description": "Large, quick-drying beach towel.",
"category": "beach",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Beach Essentials",
"shopUrl": "https://shop1.com",
"price": 15.0
}
]
},
{
"name": "Beach Ball",
"weightInGrams": 100,
"quantity": 1,
"description": "Inflatable beach ball for games.",
"category": "beach",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Beach Fun Shop",
"shopUrl": "https://shop2.com",
"price": 5.0
}
]
},
{
"name": "Sunscreen SPF 50",
"weightInGrams": 200,
"quantity": 1,
"description": "High-SPF sunscreen for beach days.",
"category": "beach",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Sunny Shop",
"shopUrl": "https://shop3.com",
"price": 10.0
}
]
},
{
"name": "Beach Chair",
"weightInGrams": 2000,
"quantity": 1,
"description": "Foldable, lightweight beach chair.",
"category": "beach",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Beach Supplies",
"shopUrl": "https://shop4.com",
"price": 25.0
}
]
}
]
}
Response file saved.
> 2024-11-04T115000.200.json

Response code: 200 (OK); Time: 549ms (549 ms); Content length: 2192 bytes (2,19 kB)

GET http://localhost:7070/api/trips/2/packing/weight

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:50:56 GMT
Content-Type: application/json
Content-Length: 4

4250
Response file saved.
> 2024-11-04T115056.200.json

Response code: 200 (OK); Time: 94ms (94 ms); Content length: 4 bytes (4 B)

GET http://localhost:7070/api/trips/2/packing

HTTP/1.1 200 OK
Date: Mon, 04 Nov 2024 10:51:14 GMT
Content-Type: application/json
Content-Encoding: gzip
Content-Length: 623

{
"id": 2,
"name": "Mountain Adventure",
"starttime": [
2024,
7,
15
],
"endtime": [
2024,
7,
20
],
"startposition": "Rocky Mountains",
"price": 800.0,
"category": "FOREST",
"guideFirstname": "Alice",
"guideLastname": "Johnson",
"packedItems": [
{
"name": "Forest Tent",
"weightInGrams": 2500,
"quantity": 1,
"description": "Durable tent for forest conditions.",
"category": "forest",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Forest Gear Shop",
"shopUrl": "https://shop1.com",
"price": 200.0
},
{
"shopName": "Adventure Supplies",
"shopUrl": "https://shop2.com",
"price": 210.0
}
]
},
{
"name": "Forest Backpack",
"weightInGrams": 800,
"quantity": 1,
"description": "Camouflage backpack for forest trekking.",
"category": "forest",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Forest Gear",
"shopUrl": "https://shop7.com",
"price": 90.0
}
]
},
{
"name": "Portable Stove",
"weightInGrams": 400,
"quantity": 1,
"description": "Compact stove for forest cooking.",
"category": "forest",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Cooking Gear",
"shopUrl": "https://shop20.com",
"price": 80.0
}
]
},
{
"name": "Forest Insect Repellent",
"weightInGrams": 100,
"quantity": 1,
"description": "Insect repellent spray for forest areas.",
"category": "forest",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Outdoor Care",
"shopUrl": "https://shop24.com",
"price": 15.0
}
]
},
{
"name": "Hiking Compass",
"weightInGrams": 50,
"quantity": 1,
"description": "Reliable compass for forest navigation.",
"category": "forest",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Outdoor Gear",
"shopUrl": "https://shop8.com",
"price": 8.0
}
]
},
{
"name": "Camping Lantern",
"weightInGrams": 300,
"quantity": 1,
"description": "LED lantern for forest camping trips.",
"category": "forest",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Camping World",
"shopUrl": "https://shop9.com",
"price": 15.0
}
]
},
{
"name": "Insect Repellent Spray",
"weightInGrams": 100,
"quantity": 1,
"description": "Spray to keep insects away in the forest.",
"category": "forest",
"createdAt": "2024-10-30T17:44:58.547Z",
"updatedAt": "2024-10-30T17:44:58.547Z",
"buyingOptions": [
{
"shopName": "Outdoor Essentials",
"shopUrl": "https://shop10.com",
"price": 7.0
}
]
}
]
}
Response file saved.
> 2024-11-04T115114.200.json

Response code: 200 (OK); Time: 82ms (82 ms); Content length: 2429 bytes (2,43 kB)

## 8.3

By properly authenticating users handling unauthorized access 
and testing different user roles I can fix the failing tests and make sure that my APIS 
security is working as I intended This will do so I get a secure application 







