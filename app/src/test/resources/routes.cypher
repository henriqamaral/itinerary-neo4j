CREATE (sao:City {name:'Sao Paulo'})
CREATE (mac:City {name:'Macapa'})
CREATE (sal:City {name:'Salvador'})
CREATE (rio:City {name:'Rio de Janeiro'})
CREATE
  (sao)-[:TO {departureTime:'9:00', arriveTime: '11:00', duration:  2}]->(mac),
  (sao)-[:TO {departureTime:'9:00', arriveTime: '19:00', duration: 10}]->(sal),
  (mac)-[:TO {departureTime:'9:00', arriveTime: '11:00', duration: 2}]->(rio),
  (rio)-[:TO {departureTime:'12:00', arriveTime: '16:00', duration: 4}]->(sal)