/* Importing packages in scala is done with the 'import' keyword.
 * In Java, one uses the '*' to import all the contents of a package.
 * The equivalent in Scala is the '_'.  However, we can also use the
 * following curly bracket syntax to import only some specific contents
 * of a package.  We can also rename the things we import with using '=>' (rocket).
 *
 * This imports the default mutable map collection, and renames it locally to MMap.
 */
import scala.collection.mutable.{ OpenHashMap => MMap }
import java.util.{ LinkedHashMap => JMap }
import scala.collection.JavaConversions._

/* In Scala, there is no "static modifier" for functions.
 * Instead, there are both "object"s and "class"es.  Objects
 * are global singletons, and their methods correspond to static
 * methods in Java.  Classes are similar to normal Java classes 
 * in that many different instances can live at the same time.
 */
object Basics { 
  
  /* Methods and functions in Scala are declared with the keyword "def".
   * 
   * Type annotations, when used, come *after* the variable they describe.
   *
   * The main entry point of a program receives an array of strings which represent the
   * command line arguments.
   */
  def main( args: Array[String] ) { 
    // We have a number of useful functions which are automatically imported into
    // every Scala program (from what we call Predef).  The "println" method is one
    // of these.  We also want an 'extra' newline.
    println("Hello Scala Basics!\n")

    /* Scala has support for nested functions, so we can define
     * a function which will be useful later right here.
     *
     * Scala supports generic functions in a manner similar to Java
     * (i.e. via type erasure).  However, Scala's type system is much
     * more expressive (in fact, it's Turing complete).  Most of the type
     * magic is beyond the scope of this intro, though.
     *
     * Here we declare a function, paramaterized on two types, K and V,
     * which takes as its argument a map from Ks to Vs.
     */
    def printMap[K,V]( m : scala.collection.Map[K,V] ) {
      /*  The Scala collections hierarchy is an impressive feat
       *  of modern typesafe engineering.  It endows most collections
       *  with a host of different useful functions; one of the most basic
       *  of which is the 'foreach' method.  This method iterates over all
       *  elements in the collection, and applies a function, purely to achieve
       *  that function's side-effect, to each element.
       */
      m.foreach{ 
       /* Each element is a key/value pair.  The case syntax is
        * an example of pattern matching; a broadly useful feature
        * which pops up a lot in Scala.  Here we match the pattern
        * of a (key,value) tuple, and bind the key to the value 'key'
        * and the value to the value 'value' (that's a lot of values!).
        *
        * The => is known as a 'rocket', and it is syntactic sugar which
        * allows us to define an anonymous function (i.e. lambda/closure).
        * Here, the function simply prints the key and value, separated by
        * an '->'.
        */
        case (key, value) => println(key+" -> "+value)
      }
      println
    }

    /* Scala has both variables ("var") and values ("val").
     * Variables can be reassigned to items of the same type.
     */
    var varHelloMap = MMap( "hello" -> "scala" )
    printMap( varHelloMap )
    varHelloMap = MMap("hello" -> "again")
    printMap( varHelloMap )

    // Values, on the other hand are immutable references; they cannot be reassigned.
    val valHelloMap = MMap( "hello" -> "value" )
    printMap( valHelloMap )
    // However, values ensure that the *reference* is immutable, not the thing
    // being referred to.  Thus, we can add things to the map.
    valHelloMap += "new" -> "entry"
    printMap( valHelloMap )

    /* Notice that I've been creating these maps without using the 'new' keyword.
     * This is quite a common idiom in Scala, and is enabled by the use of 'companion'
     * objects.  If one defines a 'class A' as well as an 'object A', they are said to
     * be companions.  The companion object can access all members of the class; including
     * those which are private.  Since it also shares a name with the class, it is a syntactially
     * nice way to provide a host of natural looking constructors for the class.  These constructors
     * are just methods of the object which return new instances of their associated companion class.
     *
     * However, if you want (or need) to, you can construct instances of the class
     * directly using the 'new' operator.  But the default constructor for Maps lacks
     * the nice syntax of the companion method we used above.  This also means that we
     * have to provide explicit type information (i.e. our map is from Strings to Strings).
     */
    val instanceMap = new MMap[String, String]
    instanceMap += ("yay" -> "scala")
    printMap( instanceMap )
    
    println("\n Here's an array")
    val a = Array.fill( 20 )( scala.util.Random.nextInt % 10 )
    println( a.mkString(",") )

    println("\nFiltering for the negative elements")
    val smallA = a.filter{ x => x < 0 }
    println( smallA.mkString(",") )

    println("\nFiltering for the non-negative elements")
    val bigA = a.filter{ x => x >= 0 }
    println( bigA.mkString(",") +"\n" )

    println("""Finding both at once with 'partition'""")
    val (partSmall, partBig) = a.partition{ x => x < 0 }
    println( partSmall.mkString(",") )
    println( partBig.mkString(",") +"\n")

    /* Speaking of type information; notice how one can often leave off explicit
     * type declarations.  This is because Scala supports limited type-inference,
     * and types can ofent be inferred uniquely by the compiler.  There are certain
     * places where types are never inferred (i.e. the arguments to a function).
     * However, you'll find that most of the time, the compiler can infer the
     * types of your variables very well.
     *
     * In general, type information flows from right to left across a line and from
     * the arguments of a function down into that function's body.  For example:
     */
    
    /* There are a few things to notice here.  First, the end of the method declaration
     * ends in '='.  This tells Scala that the function returns a meaningful result.
     * Functions declared without the '=' have a return type of 'Unit', which you can think
     * of as 'void'.
     *
     * Second, look how w is declared; no type annotations.  This is because the compiler
     * is able infer w's type.  Since both v and 1 are integers, w must be one as well.
     *
     * Third, observe the semicolon.  More importantly, observe the lack of semicolons in
     * the surrounding code.  Semicolons are statement / expression separators in Scala, but
     * are optional most of the time.  Usually, a new line denotes the end of the preceding
     * statement.  However, the semicolon is sometimes useful, as it allows you to put multiple
     * statements on the same line.
     * 
     * Finally, look how we leave off the return keyword.  By default, Scala will return the
     * last evaluated expression of a function.  It is idiomatic Scala to leave off the return
     * keyword where possible, and you'll find that 'return' is unnecessary most of the time.
     */
    def addOne( v : Int ) = { val w = v+1; w }

    println("""Creating a new array with 'map'""")
    // Create a new Array by applying the given function (addOne) to 
    // each element in 'a'
    val aPlus = a.map{ addOne }
    println( aPlus.mkString(",") +"\n" )

    println("""The original numbers in a 'Set'""")
    val aSet = a.toSet
    println( aSet.mkString(",") +"\n" )
    
    println("""If we map over a 'Set', we get back a 'Set', not some more generic 'list' type as in python""")
    val aSetPlus = aSet.map{ addOne }
    println( aSetPlus.mkString(",") +"\n" )

    def humanString( v:Int ) = { 
     /* conditional expressions evaluate to either
      * their if clause, or their else clause; so we
      * can directly assign the result of a conditional to
      * a variable or value.
      */
      val neg = if ( v < 0 ) { "negative " } else { "" }

      /* 
       * Pattern matching in Scala is a bit like switch 
       * statements on steroids.  Here we just use it like
       * a switch, but it's capable of much more.  For example, we
       * can match on anything; not just numbers.  Also, we can extract
       * the sub-parts of an object when matching (like the case(key,value)
       * used in the printMap function above), and incorporate matching
       * guards (arbitrary functions which evaluate to a Boolean).
       */
      val str = scala.math.abs(v) match { 
        case 0 => "zero"
        case 1 => "one"
        case 2 => "two"
        case 3 => "three"
        case 4 => "four"
        case 5 => "five"
        case 6 => "six"
        case 7 => "seven"
        case 8 => "eight"
        case 9 => "nine"
        case 10 => "ten"
        case _ => "WHOOPS"
      }
      neg + str
    }

    println("An array of human-readable strings")
    val aStr = aPlus.map{ humanString }
    println( aStr.mkString(", ") +"\n" )

    // Create a new person object
    val rob = new Person("Rob", 27)
    // Print it out using our toString method
    println(rob)
    
    // Notice how we can access the fields we marked with val/var
    println("But soon, "+rob.name+" will be "+(rob.age+1))
    // And change the ones we marked with var
    rob.age += 1
    println(rob+"?")

    // Scala has very good interoperability with Java
    println("\n Using Java")
    // We can use java collections
    val jmap = new JMap[String,String]
    jmap.put("hello", "Java")
    jmap.put("meet", "Scala")
    // And even endow them with some Scala functionality
    println(jmap.mkString(", "))

  }

  /*
  * Let's define a simple class so we can take a look at some of the fun things Scala
  * provides.
  *
  * We specify the arguments to the primary class constructor directly in the class definiton.
  * Here, the person class takes a String and an Int which represent the person's name and age.
  * When we put a val or a var in front of the argument in a class definition, that tells the
  * Scala compiler to automatically generate "getters" and/or "setters" for those variables.
  *
  * The things annoated with 'val' will have getters created, so that we can refer to things
  * like PersonInstance.name.
  *
  * The things annotated with 'var' will, additionally, have setters, so we can do things
  * like PersonInstance.age += 1.
  */
  class Person( val name:String, var age:Int ) { 
    override def toString() = { "Person "+name+" is "+age+" years old" }
  }
  

}
