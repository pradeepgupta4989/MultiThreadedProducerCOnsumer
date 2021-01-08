# MultiThreadedProducerConsumer
- Producer and Consumer threads will execute in multi threaded env to update shared counter variable.
- Number of Producer and Consumer thread counter will be persisted in H2 (In-memory) DB.
- Timestamp of the counter and thread details persisted to H2 (In-memory) DB once counter value reaches 0 or 100.

#REST APIs :
1. POST : http://{SERVER}:{PORT}/producerConsumer/v1/createThreads/{ProducerThreadCount}/{ConsumerThreadCount}
   - eg. http://localhost:8888/producerConsumer/v1/createThreads/4/5
   - Sample response : {
              "STATUS": "SUCCESS"
             }
   - Validations :- ProducerThreadCount and ConsumerThreadCount value should be within 0 to 50 (exclusive) range.
  
2. PUT : http://{SERVER}:{PORT}/producerConsumer/v1/updateCounter/{CounterValue}  
   - eg. http://localhost:8888/producerConsumer/v1/updateCounter/60     
   - Sample response : {
              "STATUS": "SUCCESS"
             }
   - Validations :- Counter Value should be within 0 to 100 (exclusive) range.   
  
  DB details : 
   - URL : http://localhost:8888/h2-console 
  
  
  
