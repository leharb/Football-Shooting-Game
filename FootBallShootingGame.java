import java.util.Random;

import comp102x.ColorImage;
import comp102x.assignment.GameLogic;
import comp102x.assignment.GameRecord;
import comp102x.assignment.Goal;

public class StudentLogic implements GameLogic{
    
    /** generateIntermediateFootballImage is a method that returns an object of ColorImage type.
     * Takes 10 arguments of which 9 are of primitive type.
     * @param ColorImage[] depthImages      An array with name deptImages of ColorImage type that containes a set of football images.
     * @param initialStep                   The initial step of the shooting animation.
     * @param currentStep                   The current step of the shooting animation.
     * @param finalStep                     The final step of the shooting animation.
     * @param initialScale                  The initial scale of the football.
     * @param finalScale                    The final scale of the football.
     * @param initialX                      The initial X position of the football.
     * @param finalX                        The final X position of the football.
     * @param initialY                      The initial X position of the football.
     * @param finalY                        The final Y position of the football.
     */
    public ColorImage generateIntermediateFootballImage(ColorImage[] depthImages, int initialStep, int currentStep, int finalStep, double initialScale, double finalScale, int initialX, int finalX, int initialY, int finalY) {
        
        int imageIndex;
        double xPosition,yPosition,scale;
        imageIndex=(int)((depthImages.length-1)*((double)currentStep-(double)initialStep)/((double)finalStep-(double)initialStep));
        //currentStep and initialStep have been typecast as 'double' to avoid imprecision during division of integers. 
        //'currentStep-initialStep' is an integer which when divided by 'finalStep-initialStep',also an integer might result in 0 or imprecision, leading to errors.
        //Converting them to double avoids this error.
        //Since imageIndex should always be an integer, the final answer has been typecast as 'int' before it is assigned to imageIndex.
        xPosition=initialX + (finalX-initialX)*((double)currentStep-(double)initialStep)/((double)finalStep-(double)initialStep);
        yPosition=initialY + (finalY-initialY)*((double)currentStep-(double)initialStep)/((double)finalStep-(double)initialStep);
        depthImages[imageIndex].setX((int)xPosition);//Since the method setX takes only 'int' as a parameter, xPosition, which is a 'double' has been typecast as 'int'.
        depthImages[imageIndex].setY((int)yPosition);//Since the method setY takes only 'int' as a parameter, yPosition, which is a 'double' has been typecast as 'int'.
        scale=initialScale + (finalScale-initialScale)*((double)currentStep-(double)initialStep)/((double)finalStep-(double)initialStep);
        depthImages[imageIndex].setScale(scale);//No need to typecast scale as 'int' because the method setScale takes a 'double' as a parameter.
        return depthImages[imageIndex];
        
        

        

    }
    /** updateGoalPositions is a method that swaps a movable goal with another one provided the latter has been hit.
     *  @param Goal[][] goals      A 2D array of Goal type that containes images of goalposts.
     *  This method scans through the entire array goal by goal and if an 'already hit goal' is encountered(the method isHit() is called to check this), it looks for a 'MOVABLE' goal adjacent to it(vertically,horizontally and diagonally).
     *  If a MOVABLE goal adjacent to the 'already hit goal' is found(the method getType() is called to check this), the method swaps the two of them. It then runs the next iteration by using the keyword 'continue'.
     *  If it isn't found, the loop simply runs the next iteration without doing anything.
     *  
     */

    public void updateGoalPositions(Goal[][] goals) {
        
        int i,j,rows=goals.length,col=goals[0].length;//The variable 'rows' contains the number of rows in the 2D array. goals.length is used to calculate the number of rows.
        //The variable 'col' contains the number of columns(goalposts) in every row of the 2D array, each row having the same number of columns(goalposts) as is seen on the canvas when the game is run.
        //goals[0].length is used to calculate the number of columns(goalposts) in every row. Any other index apart from 0 can be used provided it is less than or equal to the 'number of rows minus one'. 
        Goal temp; // 'temp' is a variable of 'Goal' type which is used later in the method to swap two goalpost images in the 'goals' array.
        for(i=0;i<rows;i++)
        {
            for(j=0;j<col;j++)
            {
                if(goals[i][j].isHit()) //The method isHit() returns 'true' if the goalpost is hit, else it returns 'false'.
                //Since there can be maximum 8 adjacent positions to a particular goalpost in the array(right,left,below,above,bottom right,top right,bottom left and top left),8 'if' conditions follow, one for each adjacent position
                //to check if it is MOVABLE. If any one of these is found MOVABLE, the two goalposts get swapped and the program breaks out of the inner 'for' loop. It then runs the next iteration.
                {
                    if(goals[i][j+1].getType()==2&&(j+1)<col) //goals[i][j+1] corresponds to the goalpost just next to the hit goalpost, on its RIGHT.
                                                             //The method getType() returns 1 if the goalpost is STATIONARY & 2 if it is MOVABLE.
                                                             //The condition in 'if' will be true iff the goalpost is MOVABLE (i.e. when getType() returns 2) and its column index is less than the number of columns in the 2D array,
                                                             // the row index always being within range(0 to 'rows-1')as specified in the outer 'for' loop.
                    {                                        
                        temp=goals[i][j];
                        goals[i][j]=goals[i][j+1];
                        goals[i][j+1]=temp;        
                                                             //The above three statements swap two images of 'goals' array.
                        goals[i][j].setType(1);    
                                                             //Since a goalpost which once has been moved can't be moved again, the swapped goalpost is set as STATIONARY by calling the method setType() with argument 1
                                                             //setType(1) sets it is STATIONARY and setType(2) sets it as MOVABLE.
                        
                        continue;                            //Since a goalpost at one of the adjacent positions has been found MOVABLE, there is no need to scan through the remaining adjacent positions.
                                                             //'continue' runs the next iteration in the inner 'for' loop without having to scan through the remaining adjacent positions.
                      }
                      if(goals[i][j-1].getType()==2&&(j-1)>=0)//goals[i][j-1] corresponds to the goalpost just next to the hit goalpost, on its LEFT.
                                                              //The condition in 'if' will be true iff the goal is MOVABLE (i.e. when getType() returns 2) and its column index is greater than or equal to 0,
                                                              // the row index always being within range(0 to 'rows-1')as specified in the outer 'for' loop.
                                                              
                      {
                          temp=goals[i][j];
                          goals[i][j]=goals[i][j-1];
                          goals[i][j-1]=temp;
                          goals[i][j].setType(1); 
                          continue;
                        }
                      if(goals[i+1][j].getType()==2&&(i+1)<rows)//goals[i+1][j] corresponds to the goalpost just BELOW the hit goalpost.
                                                                //The condition in 'if' will be true iff the goal is MOVABLE (i.e. when getType() returns 2) and its row index is less than the number of rows in the 2D array,
                                                                // the column index always being within range(0 to 'col-1')as specified in the inner 'for' loop.
                       
                      {
                          temp=goals[i][j];
                          goals[i][j]=goals[i+1][j];
                          goals[i+1][j]=temp;
                          goals[i][j].setType(1);
                          continue;
                        }
                      if(goals[i-1][j].getType()==2&&(i-1)>=0)//goals[i-1][j] corresponds to the goalpost just ABOVE the hit goalpost.
                                                              //The condition in 'if' will be true iff the goalpost is MOVABLE(i.e. when getType() returns 2) and its row index is greater than or equal to 0,
                                                              //the column index always being within range(0 to 'col-1') as specified in the inner 'for' loop.
                                                              
                      {
                          temp=goals[i][j];
                          goals[i][j]=goals[i-1][j];
                          goals[i-1][j]=temp;
                          goals[i][j].setType(1);
                          continue;
                        }
                      if(goals[i+1][j+1].getType()==2&&(i+1)<rows&&(j+1)<col)//goals[i+1][j+1] corresponds to the goalpost at the BOTTOM RIGHT of the hit goalpost.
                                                                             //The condition in 'if' will be true iff the goalpost is MOVABLE(i.e. when getType() returns 2) and its row index and column index, both are less than 
                                                                             //the number of rows and the number of columns respectively.
                      {
                          temp=goals[i][j];
                          goals[i][j]=goals[i+1][j+1];
                          goals[i+1][j+1]=temp;
                          goals[i][j].setType(1);
                          continue;
                        }
                      if(goals[i-1][j+1].getType()==2&&(i-1)>=0&&(j+1)<col)//goals[i-1][j+1] corresponds to the goalpost at the TOP RIGHT of the hit goalpost.
                                                                           //The condition in 'if' will be true iff the goalpost is MOVABLE(i.e. when getType() returns 2) and its row index is greater than or equal to 0 and also when  
                                                                           //its column index is less than the number of columns.
                      {
                          temp=goals[i][j];
                          goals[i][j]=goals[i-1][j+1];
                          goals[i-1][j+1]=temp;
                          goals[i][j].setType(1);
                          continue;
                        }
                      if(goals[i+1][j-1].getType()==2&&(i+1)<rows&&(j-1)>=0)//goals[i+1][j-1] corresponds to the goalpost at the BOTTOM LEFT of the hit goalpost.
                                                                            //The condition in 'if' will be true iff the goalpost is MOVABLE(i.e. when getType() returns 2)and its row index is less than the number of rows and also 
                                                                            //when its column index is greater than or equal to 0.
                      {
                          temp=goals[i][j];
                          goals[i][j]=goals[i+1][j-1];
                          goals[i+1][j-1]=temp;
                          goals[i][j].setType(1);
                          continue;
                        }
                      if(goals[i-1][j-1].getType()==2&&(i-1)>=0&&(j-1)>=0)//goals[i-1][j-1] corresponds to the goalpost at the TOP LEFT of the hit goalpost.
                      {                                                   //The condition in 'if' will be true iff the goalpost is MOVABLE(i.e. when getType() returns 2)and its row index and column index both are greater than or 
                                                                          //equal to 0.
                          temp=goals[i][j];
                          goals[i][j]=goals[i-1][j-1];
                          goals[i-1][j-1]=temp;
                          goals[i][j].setType(1);
                          continue;
                        }
                  }
              }
          }
          
          
          
          
          
                  }
                      
                        
                    
                    
                      
                          
                          
                        
                        
                        
                    
                
                
    
        
        
        
        
    

    /** updateHighScoreRecords is a method that updates the high scores of the game with name,level and score of the player.
     * Returns an array of type GameRecord.
     * @param GameRecords[] highScoreRecords        An array that containes the previous records of the game.
     * @param String name                           Name of the current player.
     * @param int level                             Level of game play of the current player.
     * @param int score                             Score of game play of the current player.
     */
    public GameRecord[] updateHighScoreRecords(GameRecord[] highScoreRecords, String name, int level, int score) {
        // write your code after this line
        int size=highScoreRecords.length,i,j,k;
        boolean flag=false;
        GameRecord[] finalrecord=new GameRecord[1];
        GameRecord temp;
        if(size==0)
        {
            finalrecord[0]=new GameRecord(name,level,score);//If size=0, i.e. if there are no previous records, a new array of size 1 is created with the name,level and score of the current player.
            
            
            
            return finalrecord;
          }
          if(size>0&&size<10)
          {   
              for(i=0;i<size;i++)
              {
                  if(highScoreRecords[i].getName().equals(name)){flag=true;
                  
                  break;}                                                  //If the name of the current player already exists in the previous records, the variable flag of 'boolean' type is set to 'true' 
                                                                           //and the program breaks out the 'for' loop.
                                                                           //The index the array element of 'highSoreRecords' which contains the name of the current player is stored in 'i'.
              }
              if(flag==true)                                              
              {   
                  GameRecord[] newrecord=new GameRecord[size];             //If flag=true, i.e. if the name of the current player already exists in the previous records, a new array 'newrecord' of type GameRecord with the size of the 
                  
                                                                           //array containing the previous records is set up.
                  for(j=0;j<i;j++)
                  newrecord[j]=new GameRecord(highScoreRecords[j].getName(),highScoreRecords[j].getLevel(),highScoreRecords[j].getScore()); //All the records of 'highScoreRecords' from index 0 to 'i-1' are copied in 'newrecord'.
                  for(j=i+1;j<size;j++)
                  newrecord[j]=new GameRecord(highScoreRecords[j].getName(),highScoreRecords[j].getLevel(),highScoreRecords[j].getScore()); //All the records of 'highScoreRecords' from index 'i+1' to 'size-1' are copied in 'newrecord'.
                  if(score>highScoreRecords[i].getScore()||(score==highScoreRecords[i].getScore()&&level>highScoreRecords[i].getLevel()))
                  newrecord[i]=new GameRecord(name,level,score); //If the current record of the current player is better than his previous record, his current record is copied in newrecord at index position 'i'.
                  else
                  newrecord[i]=new GameRecord(highScoreRecords[i].getName(),highScoreRecords[i].getLevel(),highScoreRecords[i].getScore());//If the current record of the current player is same or worse than his previous record, his 
                                       //previous record is copied in 'newrecords' at at index position 'i'. In this case, the arrays 'newrecord' and 'highScoreRecords' are exactly the same.
                  for(j=0;j<size-1;j++) //'newrecord' is now sorted in descending according to the scores of different players. If scores of two players are same, the player with the greater 'level' among the two will be given a  
                                        //higher rank.
                  {
              
                  for(k=j+1;k<size;k++)
                  {
                      if(newrecord[j].getScore()<newrecord[k].getScore()||(newrecord[j].getScore()==newrecord[k].getScore()&&newrecord[j].getLevel()<newrecord[k].getLevel()))
                          {
                           temp=newrecord[j];
                           newrecord[j]=newrecord[k];
                           newrecord[k]=temp;
                          }
                      }
                  }
                  return newrecord;
              }
              if(flag==false) 
              {
              
              GameRecord[] newrecord=new GameRecord[size+1]; //if flag=false, i.e. if the name of the current player is NOT already there in the previous records, a new array 'newrecord' of 'GameRecords' type  is created with size
                                                             //one more than that of the array containing the previous records.
              for(j=0;j<size;j++)
              {
                  newrecord[j]=new GameRecord(highScoreRecords[j].getName(),highScoreRecords[j].getLevel(),highScoreRecords[j].getScore()); //All the records of 'highScoreRecords' are copied in 'newrecords'.
              }
              newrecord[size]=new GameRecord(name,level,score); //The current record is also copied in 'newrecords' at index position size.
              for(j=0;j<size;j++) //'newrecord' is now sorted in descending according to the scores of different players. If scores of two players are same, the player with the greater 'level' among the two will be given a  
                                        //higher rank.
              {
                  for(k=j+1;k<size+1;k++)
                  {
                      if(newrecord[j].getScore()<newrecord[k].getScore()||(newrecord[j].getScore()==newrecord[k].getScore()&&newrecord[j].getLevel()<newrecord[k].getLevel()))
                          {
                           temp=newrecord[j];
                           newrecord[j]=newrecord[k];
                           newrecord[k]=temp;
                          }
                      }
                  }
              return newrecord;
          }
          
}
if(size==10)
{flag=false;
    for(i=0;i<10;i++)
              {
                  if(highScoreRecords[i].getName().equals(name)){flag=true;
                  break;}                                                   //If the name of the current player already exists in the previous records, the variable flag of 'boolean' type is set to 'true' 
                                                                           //and the program breaks out the 'for' loop.
                                                                           //The index the array element of 'highSoreRecords' which contains the name of the current player is stored in 'i'.
              }
                 if(flag==true)
              {   
                  GameRecord[] newrecord=new GameRecord[10];             //If flag=true, i.e. if the name of the current player already exists in the previous records, a new array 'newrecord' of type GameRecord with the size of the 
                  
                                                                         //array(=10) containing the previous records is set up.
                  for(j=0;j<i;j++)
                  newrecord[j]=new GameRecord(highScoreRecords[j].getName(),highScoreRecords[j].getLevel(),highScoreRecords[j].getScore());  //All the records of 'highScoreRecords' from index 0 to 'i-1' are copied in 'newrecord'.
                  for(j=i+1;j<size;j++)
                  newrecord[j]=new GameRecord(highScoreRecords[j].getName(),highScoreRecords[j].getLevel(),highScoreRecords[j].getScore()); //All the records of 'highScoreRecords' from index 'i+1' to 'size-1' are copied in 'newrecord'.
                  if(score>highScoreRecords[i].getScore()||(score==highScoreRecords[i].getScore()&&level>highScoreRecords[i].getLevel()))
                  newrecord[i]=new GameRecord(name,level,score);     //If the current record of the current player is better than his previous record, his current record is copied in newrecord at index position 'i'.                                                                       
                  else
                  newrecord[i]=new GameRecord(highScoreRecords[i].getName(),highScoreRecords[i].getLevel(),highScoreRecords[i].getScore());//If the current record of the current player is same or worse than his previous record, his 
                                       //previous record is copied in 'newrecord' at at index position 'i'. In this case, the arrays 'newrecord' and 'highScoreRecords' are exactly the same.
                  for(j=0;j<9;j++) //'newrecord' is now sorted in descending according to the scores of different players. If scores of two players are same, the player with the greater 'level' among the two will be given a  
                                   //higher rank.
                  {
              
                  for(k=j+1;k<10;k++)
                  {
                      if(newrecord[j].getScore()<newrecord[k].getScore()||(newrecord[j].getScore()==newrecord[k].getScore()&&newrecord[j].getLevel()<newrecord[k].getLevel()))
                          {
                           temp=newrecord[j];
                           newrecord[j]=newrecord[k];
                           newrecord[k]=temp;
                          }
                      }
                  }
                  return newrecord;
              }
              
                  
              if(flag==false)
              {
                  GameRecord[] temprecord=new GameRecord[11];
                  GameRecord[] newrecord = new GameRecord[10];//if flag=false, i.e. if the name of the current player is NOT already there in the previous records, a new array 'temprecord' of 'GameRecords' type  is created with size 11.
                                                             //Another array 'newrecord' of 'GameRecords' type is also set up with size 10.
                  for(j=0;j<10;j++)
                  {
                  temprecord[j]=new GameRecord(highScoreRecords[j].getName(),highScoreRecords[j].getLevel(),highScoreRecords[j].getScore()); //All the records of 'highScoreRecords' are copied in 'temprecord'.
              }
              temprecord[10]=new GameRecord(name,level,score); //The current record is also copied in 'temprecord' at index position 10'.
                                                               //'temprecord' contains 11 records.
              for(j=0;j<10;j++) //'temprecord' is now sorted in descending according to the scores of different players. If scores of two players are same, the player with the greater 'level' among the two will be given a  
                                        //higher rank.
              {
                  for(k=j+1;k<11;k++)
                  {
                      if(temprecord[j].getScore()<temprecord[k].getScore()||(temprecord[j].getScore()==temprecord[k].getScore()&&temprecord[j].getLevel()<temprecord[k].getLevel()))
                          {
                           temp=temprecord[j];
                           temprecord[j]=temprecord[k];
                           temprecord[k]=temp;
                          }
                      }
                  }
              for(j=0;j<10;j++) 
              newrecord[j]=new GameRecord(temprecord[j].getName(),temprecord[j].getLevel(),temprecord[j].getScore()); //Since 'temprecord' is now sorted in descending order, the first 10 records, i.e. the 10 best records of 'temprecord' are copied to 'newrecord'.
              return newrecord;
          }
}

              
          
          return highScoreRecords; //This statement has been used here, because in absence of a return statement, the program won't compile. This statement, though, never gets 
                                   // executed because one of the conditions above in 'updateHighScoreRecords' will always be true and the 'return' statement immediately following it will
                                   // get executed.
        
        
        
                  
                  
          
          
    
        
          
                    
            
          
            
            
        
        
        
        
    }
    
    
}

