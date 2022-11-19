// public class b {
    
// // The best known label-correcting SSSP algorithm is known as Δ-stepping, 
// // due to Meyer and Sanders [J. Algorithms, 2003].  
// // Instead of a strict priority queue, it maintains an array of buckets, 
// // where bucket i holds vertices whose currently best-known paths 
// // have lengths i × Δ ≤ l < (i+1) × Δ.  

// // Initially only the source is present in the array, in bucket 0.  
// // At each step of the algorithm, we consider the first non-empty bucket B.  
// // For each vertex v in B, we relax each neighbor w that is connected to v 
// // by an edge of weight ≤Δ—that is, 
// // we check to see if connecting through v will improve w’s path to the 
// // source, and 
// // if so, we move w to the appropriate new bucket,
// //  which might be B or one of its successors.  

// // Because we might move w into B, we iterate until B is empty; 
// // then we relax all the neighbors connected 
// // to v by edges of weight >Δ, for all edges v that we have removed from B.  

// // Finally, we move on to the next nonempty bucket.  

// // As it turns out, given a maximum weight W for edges, we can be s
// // ure that all vertices currently in the array will live 
// // within W/Δ buckets of each other.  
// // If we create a few more buckets than this, 
// // we can safely use the array in a circular fashion; 
// // we don’t actually need max-path-length/Δ of them. 

// // Over time, w’s preferred edge back to the source may be updated several times.  
// // Crucially, however, iteration over the neighbors w of v can be done in parallel for all vertices v in B.  
// // This is the source of potential parallelism.  
// // Harvesting this parallelism isn’t trivial, however.  
// // Threads must always work on the same bucket at the same point in time, 
// // Threads also must synchronize their access to all shared data structures.  

// // While there are many ways to parallelize Δ-stepping, the following is perhaps the easiest:  
// // We statically partition the n vertices so that each of our t threads is responsible for approximately n/t of them. 
// // Each thread then maintains its own, separate array of buckets.  

// // We use a barrier (e.g., Java’s CyclicBarrier) to make sure that each thread works on its ith bucket during step i (perhaps each part of step i).  
// // We also create a set of message queues (e.g., instances of Java’s ConcurrentLinkedQueue) that allow threads to pass work to one another.  
// // When thread j discovers an opportunity to relax vertex w, which belongs to thread k, it does not do so directly; 
// // rather, it enqueues a message asking k to do so.  
// // (For efficiency, j may “batch” together requests being sent to the same destination.)

//     t = Runtime.getRuntime().availableProcessors();
//     m = n / t;
//     buckets = new int[t][numBuckets];
//     barrier = new CyclicBarrier(t);
//     queues = new ConcurrentLinkedQueue[t];
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
//     }



// }
