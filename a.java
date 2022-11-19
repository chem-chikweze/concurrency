// public class a {
    


//     int n = vertices.length;
//     int t = Runtime.getRuntime().availableProcessors();
//     int m = n / t;
//     int[][] buckets = new int[t][numBuckets];

//     CyclicBarrier barrier = new CyclicBarrier(t);
//     ConcurrentLinkedQueue<Request>[] queues = new ConcurrentLinkedQueue[t];
//     for (int i = 0; i < t; i++) {
//         queues[i] = new ConcurrentLinkedQueue<Request>();
//     }
//     // It creates m buckets, each with n/m elements. 
//     // Then, it adds the vertices to each bucket.
//     for (int i = 0; i < n; i++) {
//         int j = i / m;
//         buckets[j][0].add(vertices[i]);
//     }
    
//     for (int i = 0; i < numBuckets; i++) {
//         barrier.await();
//         for (int j = 0; j < t; j++) {
//             for (Vertex v : buckets[j][i]) {
//                 for (Edge e : v.neighbors) {
//                     if (e.weight <= delta) {
//                         Vertex o = e.other(v);
//                         int k = o.id / m;
//                         queues[k].add(new Request(o, e));
//                     }
//                 }
//             }
//         }
//         barrier.await();
//         for (int j = 0; j < t; j++) {
//             Request r;
//             while ((r = queues[j].poll()) != null) {
//                 r.relax();
//             }
//         }
//         barrier.await();
//         for (int j = 0; j < t; j++) {
//             for (Vertex v : buckets[j][i]) {
//                 for (Edge e : v.neighbors) {
//                     if (e.weight > delta) {
//                         Vertex o = e.other(v);
//                         int k = o.id / m;
//                         queues[k].add(new Request(o, e));
//                     }
//                 }
//             }
//         }
//         barrier.await();
//         for (int j = 0; j < t; j++) {
//             Request r;
//             while ((r = queues[j].poll()) != null) {
//                 r.relax();
//             }
//         }
//         barrier.await();
//     }

//     // Main loop.
    
//         for (int i = 0; i < numBuckets; ++i) {
//             // Process light edges.
//             //
//             LinkedList<Request> requests = findRequests(buckets.get(i), true);
//             for (Request r : requests) {
//                 r.relax();
//             }

//             // Process heavy edges.
//             //
//             requests = findRequests(buckets.get(i), false);
//             for (Request r : requests) {
//                 r.relax();
//             }
//         }
//     }

//     // Main program.

//     public static void main(String[] args) throws Exception {
//         if (args.length != 2) {
//             System.err.println("Usage: java DeltaSolve <graph file> <max coord>");
//             System.exit(1);
//         }
//         DeltaSolve solver = new DeltaSolve(args[0], Integer.parseInt(args[1]));
//         solver.DeltaSolve();
//         solver.printSolution();
//     }
// }



// public void DeltaSolve() throws Coordinator.KilledException {
//     numBuckets = 2 * degree;
//     delta = maxCoord / degree;
//     // All buckets, together, cover a range of 2 * maxCoord,
//     // which is larger than the weight of any edge, so a relaxation
//     // will never wrap all the way around the array.
//     buckets = new ArrayList<LinkedHashSet<Vertex>>(numBuckets);
//     for (int i = 0; i < numBuckets; ++i) {
//         buckets.add(new LinkedHashSet<Vertex>());
//     }
//     buckets.get(0).add(vertices[0]);
    

//     int i = 0;
//     for (;;) {
//         LinkedList<Vertex> removed = new LinkedList<Vertex>();
//         LinkedList<Request> requests;
//         while (buckets.get(i).size() > 0) {
//             requests = findRequests(buckets.get(i), true);  // light relaxations
//             // Move all vertices from bucket i to removed list.
//             removed.addAll(buckets.get(i));
//             buckets.set(i, new LinkedHashSet<Vertex>());
//             for (Request req : requests) {
//                 req.relax();
//             }
//         }
//         // Now bucket i is empty.
//         requests = findRequests(removed, false);    // heavy relaxations
//         for (Request req : requests) {
//             req.relax();
//         }
//         // Find next nonempty bucket.
//         int j = i;
//         do {
//             j = (j + 1) % numBuckets;
//         } while (j != i && buckets.get(j).size() == 0);
//         if (i == j) {
//             // Cycled all the way around; we're done
//             break;  // for (;;) loop
//         }
//         i = j;
//     }
// }
// }
