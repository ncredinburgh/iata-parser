# iata-parser

### Description
Implements a parser for the boarding pass format described in IATA Resolution 792 - Version 5. 

This specification (in conjunction with others) can be downloaded [here](http://www.airlineinfo.com/ostpdf88/98.pdf).

### Usage

    public class Main
    {
        public static void main(String[] args) throws Exception
        {
            // Create code
            IataCode code = new Parser().parse("M1DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 100");
            
            // Print code
            try (OutputStreamWriter writer = new OutputStreamWriter(System.out))
            {
                IataCodes.walk(code, new Printer(new SpecificationFormatter(), writer));
            }
        }
    }
    
#### Strict Mode 

A parser may be configured to _strictly_ validate the supplied code according to the allowed types & values defined in 
Resolution 792. When enabled any value outside an allowed range for that element will throw a exception detailing 
the problem.

To enable "strict mode" do the following:
 
    Parser strictParser = new Parser().strict(); // Returns a new parser with strict mode enabled.

### Links

 * [IATA Resolution 792 - Version 3](http://www.iata.org/whatwedo/stb/documents/resolution792-june2010.pdf)

### Author

[NCR Edinburgh](http://ncredinburgh.com/)

### License

This project is licensed under the Apache v2 license. See the [LICENSE](LICENSE.txt) file for more info.