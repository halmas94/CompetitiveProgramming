
public class TreeDriver 
{
   public static void main(String[] args)
   {
      AVLTree<Integer> tree = new AVLTree<Integer>();
      
      for(int i = 0; i< 50; i++)
      {
         tree.insert(i);
      }
      
      tree.delete(0);
      tree.delete(4);
      tree.delete(2);
      tree.delete(7);

      AVLTree.Node six = tree.find(6);
      System.out.println(six.height);
      
   }
   
   public static class AVLTree<T extends Comparable<T>>
   {
      Node root;
   
      public AVLTree()
      {
         root = null;
      }
   
      public void insert(T val)
      {
         Node n = new Node(val);
         if(root!=null)
            root.insert(n);
         else
            root = n;
      }
   
      public void delete(T val)
      {
         if(root!=null)
            root.delete(val);
      }
   
      public Node find(T val)
      {
         return root.find(val);
      }
   
      public class Node
      {
         T value;
         int height;
         Node left, right, parent;
      
         public Node(T val)
         {
            value = val;
            height = 0;
         }
      
         public int height(Node node)
         {
            if(node!=null)
               return node.height;
            return -1;
         }
      
         public Node find(T val)
         {
            if(val.compareTo(value)==0)
               return this;  
            Node toSearch = val.compareTo(value)<0 ? left : right;
            if(toSearch == null)
               return null;
            return toSearch.find(val);
         }
      
         public void insert(Node node)
         {
            if(node.value.compareTo(value)<=0)
            {
               if(left==null)
               {
                  left = node;
                  node.parent = this;
               }
               else
                  left.insert(node);
            }
            else
            {
               if(right==null)
               {
                  right = node;
                  node.parent = this;
               }
               else
                  right.insert(node);
            }
            recalcHeight();
            rebalance();
         }
      
      
         public void delete(T val)
         {
         
            if(val.compareTo(value) == 0)
            {
            
               Node replacement = null;
               Node toDelete = this;
                     
               if(left==null || right == null)
               {
                  if(left==null && right == null)
                  {
                  }
                  else if(left == null)
                  {
                     replacement = right;
                     this.right = null;
                  }
                  else
                  {
                     replacement = left;
                     this.left = null;
                  }
               }
               else
               {
                  toDelete = this.left.maxNode();
                  replacement = toDelete.left;
                  this.value = toDelete.value;
               }
            
               if(toDelete.parent!=null)
               {
                  if(toDelete == toDelete.parent.left)
                     toDelete.parent.left = replacement;
                  else
                     toDelete.parent.right = replacement;
                  
                  if(replacement!=null)
                     replacement.parent = toDelete.parent;
                  toDelete.parent.rebalance();
                  toDelete.parent = null;
               }
               else
               {
                  root = replacement;
                  if(replacement!=null)
                     replacement.parent = null;
                  
               } 
               if(replacement!=null)
               {
                  replacement.recalcHeight();
                  replacement.rebalance();
               }
                          
            }
            else
            {
               if(val.compareTo(value)<0)
               {
                  if(left!=null)
                     left.delete(val);
               }
               else
               {
                  if(right!=null)
                     right.delete(val);
               
               }
            }
            this.recalcHeight();
            this.rebalance();
         }
      
         public Node minNode()
         {
            Node temp = this;
            while(temp.left!=null)
               temp = temp.left;
            return temp;
         }
      
         public Node maxNode()
         {
            Node temp = this;
            while(temp.right!=null)
               temp = temp.right;
            return temp;
         }
      
         public void recalcHeight()
         {
            height = 1 + Math.max(height(left), height(right));
         }
      
         public int spread()
         {
            return height(right)-height(left);
         }
      
         public void rebalance()
         {
            int diff = spread();
            if(Math.abs(diff)>1)
            {
            // right heavy
               if(diff>1)
               {
                  if(right.spread()<0)
                  {
                     right.rotateRight();
                  }
                  rotateLeft();
               }
               else
               {
                  if(left.spread()>0)
                  {
                     left.rotateLeft();
                  }    
                  rotateRight();
               }
            }
            if(parent!=null)
               parent.rebalance();
         }
      
         public void rotateLeft()
         {
            Node tempP = parent;
            Node pivot = right;
         
            right = right.left;
            if(right!=null) right.parent = this;
         
            pivot.left = this;
            pivot.parent = tempP;
            parent = pivot;
         
            if(tempP==null) root = pivot;
            else
            {
               if(this == tempP.left)
                  tempP.left = pivot;
               else
                  tempP.right = pivot;
            }
         
         
            recalcHeight();
            pivot.recalcHeight();
            if(tempP != null) tempP.recalcHeight();
         }
      
         public void rotateRight()
         {
            Node tempP = parent;
            Node pivot = left;
         
            left = pivot.right;
            if(left!=null) left.parent = this;
         
            pivot.right = this;
            pivot.parent = tempP;
            parent = pivot;
         
            if(tempP==null) root = pivot;
            else
            {
               if(this.equals(tempP.left))
                  tempP.left = pivot;
               else
                  tempP.right = pivot;
            }
         
            recalcHeight();
            pivot.recalcHeight();
            if(tempP != null) tempP.recalcHeight();  
         }
      }
   }

}