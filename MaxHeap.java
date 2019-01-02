import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class MaxHeap {

    private int[] heap;
    private int currentPosition;
    public int swaps;
    private static final int DEFAULT_CAPACITY = 25;
    private static final int MAX_CAPACITY = 10000;
    
    //Default constructor
    public MaxHeap()
    {
        this(DEFAULT_CAPACITY);
        heap = new int[DEFAULT_CAPACITY];
    }
    
    //Constructor with argument
    public MaxHeap(int initialCapacity)
    {
        if (initialCapacity < DEFAULT_CAPACITY)
            initialCapacity = DEFAULT_CAPACITY;
        else
        {
            heap = new int[initialCapacity];
            checkCapacity(initialCapacity);
        }
    }
    
    public void answers()
    {
        for (int i = 0; i < 101; i++)
            System.out.println(heap[i]);
    }
    
    //Check capacity to make sure it doesn't exceed max capacity
    private void checkCapacity(int value)
    {
        if (value > MAX_CAPACITY)
             throw new IllegalStateException("Attempt to create a heap whose capacity exceeds allowed maximum.");
    }    
    
    //This method adds values to the heap and immediately checks its parents to see if a swap needs to be made to maintain the max heap condition
    public void addSeries(int value)
    {
        heap[++currentPosition] = value;
        int childIndex = currentPosition;
        int parentValue = childIndex/2;
        while(heap[parentValue]<heap[childIndex] && parentValue > 0)
        {
            int swap = heap[parentValue];
            heap[parentValue] = heap[childIndex];
            heap[childIndex] = swap;
            swaps++;
            childIndex = parentValue;
            parentValue = childIndex/2;
        }
    }
    
    //This method adds values to the heap without editing its position, to be used for the "smart" approach
    public void addSmart(int value)
    {
        heap[++currentPosition] = value;
    }
    
    //This method returns the length of the array
    public int length()
    {
        return heap.length;
    }
    
    //This method compares the index with its children and performs swaps accordingly to form a max heap
    public void reheap(int index)
    {
        int orphan = heap[index];
        int leftChildIndex = 2 * index;
        int largerChildIndex = leftChildIndex;

        //Checking if there is a 2nd child
        if (leftChildIndex<heap.length-1)
        {
            int rightChildIndex=leftChildIndex+1;
            //If there is, check if it is bigger than the left child
            if (heap[rightChildIndex]>(heap[largerChildIndex]))
            {
                largerChildIndex = rightChildIndex;//Obtaining the larger value between the two children
            }
        }
        if (heap[index] < heap[largerChildIndex])
        {
            heap[index] = heap[largerChildIndex];
            heap[largerChildIndex] = orphan;
            //Swap is performed between the parent and the greater child
            swaps++;
            if (largerChildIndex*2<=heap.length-1)
                reheap(largerChildIndex);
        }
        if (index!=1)
        {
            index--;
            reheap(index);
        }
    }
    
    //This method is similar to reheap, but moves downwards instead of upwards
    public void downHeap(int index)
    {
        int orphan = heap[index];
        int leftChildIndex = 2 * index;
        int largerChildIndex = leftChildIndex;

        //Checking if there is a 2nd child
        if (leftChildIndex<heap.length-1)
        {
            int rightChildIndex=leftChildIndex+1;
            //If there is, check if it is bigger than the left child
            if (heap[rightChildIndex]>(heap[largerChildIndex]))
            {
                largerChildIndex = rightChildIndex;//Obtaining the larger value between the two children
            }
        }
        if (heap[index] < heap[largerChildIndex])
        {
            heap[index] = heap[largerChildIndex];
            heap[largerChildIndex] = orphan;
            //Swap is performed between the parent and the greater child
            swaps++;
            if (largerChildIndex * 2 < heap.length-1)
                downHeap(largerChildIndex);
        }
    }
    
    //This method retrieves the index of the last nonzero element in an array, used in the removal method
    public int findLastNonzero()
    {
        for (int i = 1; i <= heap.length-1; i++)
        {
            if (heap[i]==0)
                return (i-1);
        }
        return 100;
    }
    
    //This method removes a certain index, calls on downheap afterwards if a swap is performed and there are further levels to examine
    int i = 1;
    public void removal(int index)
    {
        int lastIndex = heap[findLastNonzero()];
        heap[index] = lastIndex;//Assuming index is root
        heap[findLastNonzero()] = 0;
        int leftChildIndex = 2 * index;
        int largerChildIndex = leftChildIndex;
        if (leftChildIndex<heap.length-1)
        {
            int rightChildIndex=leftChildIndex+1;
            //If there is, check if it is bigger than the left child
            if (heap[rightChildIndex]>(heap[largerChildIndex]))
            {
                largerChildIndex = rightChildIndex;//Obtaining the larger value between the two children
            }
        }
        if (heap[index] < heap[largerChildIndex])
        {
            int orphan = heap[index];
            heap[index] = heap[largerChildIndex];
            heap[largerChildIndex] = orphan;
            if (largerChildIndex * 2 <= heap.length-1)
            {
                downHeap(largerChildIndex);
            }
        }    
    }
    
    //This method adds numbers 1-100 to the array
    public void addFixed()
    {
        for (int i = 1; i < heap.length; i++)
        {
            heap[i] = i;
        }
    }
    
    //This method prints the first ten numbers of the array with proper formatting
    public void firstTen()
    {
        for (int i = 1; i < 11; i++)
        {
            System.out.print(heap[i] + ",");
        }
        System.out.print("...");
    }
    
    //This method clears the array and sets the swaps/currentPosition back to 0
    public void clearArray()
    {
        for (int i = 0; i < heap.length; i++)
        {
            heap[i] = 0;
        }
        currentPosition = 0;
        swaps = 0;
    }
       
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        Random random = new Random();
        //The arraylist is to ensure there are no duplicates if option 1 is chosen
        ArrayList<Integer> inputs = new ArrayList<>();
        MaxHeap max_series = new MaxHeap(101);
        MaxHeap max_smart = new MaxHeap(101);
        
        System.out.print("Please select how to test the program:\n(1) 20 sets of 100 randomly generated integers\n(2) Fixed integer values 1-100\nEnter choice: ");
        int choice = kb.nextInt();
        System.out.println();
        if (choice == 1)
        {
            int sets = 1;
            int totalNormal = 0;
            int totalSmart = 0;
            //20 sets of 100 randomly generated integers are performed
            while (sets < 21)
            {
                int count = 0; 
                while (count < 100)
                {
                    int val = random.nextInt(100)+1;
                    //This ensures that numbers added into the array are unique
                    while (inputs.contains(val))
                        val = random.nextInt(100)+1;
                    max_series.addSeries(val);
                    inputs.add(val);
                    count++;
                }
                totalNormal += max_series.swaps;
                max_series.clearArray(); 
                
                for (int i = 0; i < inputs.size(); i++)
                    max_smart.addSmart(inputs.get(i));
                    //adding values to the second array in the same order
                max_smart.reheap(max_smart.length()/2);
                totalSmart += max_smart.swaps; 
                max_smart.clearArray();
                inputs.clear();
                sets++;
            }
            System.out.println("Aveage swaps for series of insertions: " + totalNormal/(sets-1));
            System.out.println("Aveage swaps for optimal method:  " + totalSmart/(sets-1));
        }
        else if (choice == 2)
        {
            MaxHeap max_fixed = new MaxHeap(101);
            for (int i = 1; i < 101; i++)
            {
                max_fixed.addSeries(i);
            }
            int swapsSeries = max_fixed.swaps;
            System.out.print("Heap built using series of insertions: ");
            max_fixed.firstTen();
            System.out.println("\nNumber of swaps: " + swapsSeries);
            for (int i = 0; i < 10; i++)
                max_fixed.removal(1);
            System.out.print("Heap after ten removals: ");
            max_fixed.firstTen();
            max_fixed.clearArray();
            
            max_fixed.addFixed();
            max_fixed.reheap(max_fixed.length()/2);
            int swapsOptimal = max_fixed.swaps;
            System.out.print("\n\nHeap built using optimal method: ");
            max_fixed.firstTen();
            System.out.println("\nNumber of swaps: " + swapsOptimal);
            for (int i = 0; i < 10; i++)
                max_fixed.removal(1);
            System.out.print("Heap after ten removals: ");
            max_fixed.firstTen();
            System.out.println();
        }
        else
            System.out.println("Invalid input!");
    }
}
