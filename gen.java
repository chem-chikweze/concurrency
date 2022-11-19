// public class gen {

// }

// // statically partition the n vertices so that
// // each of our t threads is responsible for approximately n/t of them
// int t = Runtime.getRuntime().availableProcessors();
// int verticesPerThread = n/t;

// int remainder = n%t;
// int[][] partitions = new int[t][];
// for (int i = 0; i < t; i++) {
// partitions[i] = new int[verticesPerThread];
// }
// for (int i = 0; i < remainder; i++) {
// partitions[i] = new int[verticesPerThread+1];
// }

// int index = 0;
// for (int i = 0; i < t; i++) {
// for (int j = 0; j < partitions[i].length; j++) {
// partitions[i][j] = index;
// index++;
// }
// }
// // Each thread then maintains its own, separate array of buckets.
// // Each thread is responsible for relaxing the requests that fall into its
// own buckets.
// Thread[] threads = new Thread[t];
// for (int i = 0; i < t; i++) {
// final int threadIndex = i;
// threads[i] = new Thread(new Runnable() {
// public void run() {
// try {
// for (int i = 0; i < numBuckets; ++i) {
// LinkedList<Request> requests = findRequests(buckets.get(i), true);
// for (Request r : requests) {
// r.relax();
// }
// }
// } catch (Coordinator.KilledException e) {
// System.out.println("Thread " + threadIndex + " killed");
// }
// }
// });
// threads[i].start();
// }

// for (int i = 0; i < numBuckets; ++i) {
// LinkedList<Request> requests = findRequests(buckets.get(i), true);
// for (Request r : requests) {
// r.relax();
// }
// }

// // We statically partition the n vertices so that each of our t threads is
// responsible for approximately n/t of them.
// // Each thread then maintains its own, separate array of buckets.
// // We use a barrier (e.g., Java’s CyclicBarrier) to make sure that each
// thread works on its ith bucket during step i (perhaps each part of step i).
// // We also create a set of message queues (e.g., instances of Java’s
// ConcurrentLinkedQueue) that allow threads to pass work to one another.
// // When thread j discovers an opportunity to relax vertex w, which belongs to
// thread k, it does not do so directly;
// // rather, it enqueues a message asking k to do so.
// // (For efficiency, j may “batch” together requests being sent to the same
// destination.)
// // code in Java

// int n = vertices.length;
// int t = Runtime.getRuntime().availableProcessors();
// int m = n / t;
// int[][] buckets = new int[t][numBuckets];

// CyclicBarrier barrier = new CyclicBarrier(t);
// ConcurrentLinkedQueue<Request>[] queues = new ConcurrentLinkedQueue[t];
// for (int i = 0; i < t; i++) {
// queues[i] = new ConcurrentLinkedQueue<Request>();
// }
// for (int i = 0; i < n; i++) {
// int j = i / m; // first m vertices go to thread 0, next m vertices go to
// thread 1, etc.
// buckets[j][0].add(vertices[i]); // add vertex to thread's bucket
// }
// for (int i = 0; i < numBuckets; i++) {
// barrier.await();
// for (int j = 0; j < t; j++) {
// for (Vertex v : buckets[j][i]) {
// for (Edge e : v.neighbors) {
// if (e.weight <= delta) {
// Vertex o = e.other(v);
// int k = o.id / m;
// queues[k].add(new Request(o, e));
// }
// }
// }
// }
// barrier.await();
// for (int j = 0; j < t; j++) {
// Request r;
// while ((r = queues[j].poll()) != null) {
// r.relax();
// }
// }
// barrier.await();
// for (int j = 0; j < t; j++) {
// for (Vertex v : buckets[j][i]) {
// for (Edge e : v.neighbors) {
// if (e.weight > delta) {
// Vertex o = e.other(v);
// int k = o.id / m;
// queues[k].add(new Request(o, e));
// }
// }
// }
// }
// barrier.await();
// for (int j = 0; j < t; j++) {
// Request r;
// while ((r = queues[j].poll()) != null) {
// r.relax();
// }
// }
// barrier.await();
// }

// // We statically partition the n vertices so that each of our t threads is
// responsible for approximately n/t of them.
// // Each thread then maintains its own, separate array of buckets.
// // We use a barrier (e.g., Java’s CyclicBarrier) to make sure that each
// thread works on its ith bucket during step i (perhaps each part of step i).
// // We also create a set of message queues (e.g., instances of Java’s
// ConcurrentLinkedQueue) that allow threads to pass work to one another.
// // When thread j discovers an opportunity to relax vertex w, which belongs to
// thread k, it does not do so directly;
// // rather, it enqueues a message asking k to do so.
// // (For efficiency, j may “batch” together requests being sent to the same
// destination.)
// // code in Java

// int n = vertices.length;
// int t = Runtime.getRuntime().availableProcessors();
// int m = n / t;
// int[][] buckets = new int[t][numBuckets];

// CyclicBarrier barrier = new CyclicBarrier(t);
// ConcurrentLinkedQueue<Request>[] queues = new ConcurrentLinkedQueue[t];
// for (int i = 0; i < t; i++) {
// queues[i] = new ConcurrentLinkedQueue<Request>();
// }

// for (int i = 0; i < n; i++) {
// int j = i / m; // first m vertices go to thread 0, next m vertices go to
// thread 1, etc.
// buckets[j][0].add(vertices[i]); // add vertex to thread's bucket
// }

// for (int i = 0; i < numBuckets; i++) {
// barrier.await();
// for (int j = 0; j < t; j++) {
// for (Vertex v : buckets[j][i]) {
// for (Edge e : v.neighbors) {
// if (e.weight <= delta) {
// Vertex o = e.other(v);
// int k = o.id / m;
// queues[k].add(new Request(o, e));
// }
// }
// }
// }
// barrier.await();
// for (int j = 0; j < t; j++) {
// Request r;
// while ((r = queues[j].poll()) != null) {
// r.relax();
// }
// }
// barrier.await();
// for (int j = 0; j < t; j++) {
// for (Vertex v : buckets[j][i]) {
// for (Edge e : v.neighbors) {
// if (e.weight > delta) {
// Vertex o = e.other(v);
// int k = o.id / m;
// queues[k].add(new Request(o, e));
// }
// }
// }
// }
// barrier.await();
// for (int j = 0; j < t; j++) {
// Request r;
// while ((r = queues[j].poll()) != null) {
// r.relax();
// }
// }
// barrier.await();
// }
// }

// // Parallel Bellman-Ford algorithm
// public void DeltaSolve() throws Coordinator.KilledException {
// // Parallel Bellman-Ford algorithm
// 1

// // numBuckets = 2 * degree;
// // delta = maxCoord / degree;
// // // All buckets, together, cover a range of 2 * maxCoord,
// // // which is larger than the weight of any edge, so a relaxation
// // // will never wrap all the way around the array.
// // buckets = new ArrayList<LinkedHashSet<Vertex>>(numBuckets);
// // for (int i = 0; i < numBuckets; ++i) {
// // buckets.add(new LinkedHashSet<Vertex>());
// // }
// // buckets.get(0).add(vertices[0]);

// // int i = 0;
// // for (;;) {
// // LinkedList<Vertex> removed = new LinkedList<Vertex>();
// // LinkedList<Request> requests;
// // while (buckets.get(i).size() > 0) {
// // requests = findRequests(buckets.get(i), true); // light relaxations
// // // Move all vertices from bucket i to removed list.
// // removed.addAll(buckets.get(i));
// // buckets.set(i, new LinkedHashSet<Vertex>());
// // for (Request req : requests) {
// // req.relax();
// // }
// // }
// // // Now bucket i is empty.
// // requests = findRequests(removed, false); // heavy relaxations
// // for (Request req : requests) {
// // req.relax();
// // }
// // // Find next nonempty bucket.
// // int j = i;
// // do {
// // j = (j + 1) % numBuckets;
// // } while (j != i && buckets.get(j).size() == 0);
// // if (i == j) {
// // // Cycled all the way around; we're done
// // break; // for (;;) loop
// // }
// // i = j;
// // }
// // }