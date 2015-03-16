Introduction of the concept of Hypergraphs for the web pages reputation techniques like PageRank or Indegree methods.

Adding this concept, we have modified the calculation of this methods into these new methods:

HyperHostPageRank
HyperDomPageRank
HyperHostIndegree
HyperDomIndegree

The application in here it's based in an RI application, except for the web crawler.
The crawler's functions were made by hand in a set of pages to make the necessary test.

The design of the application contains:

INDEXER: it's a module that index a sample of pages and then it gives ranking to every pages based on four different page ranking methods.

SEARCH GUI: this module was a PHP interfaces that allow you to make a search of some particular values like if you were making a google search. (this values are restricted to the set of pages made for the test)

RESULTS:
After you indexed the pages, you will be able to order this results for this four methods and analyze how each works and how is better for every particular case of search.

