
public void problem1PartA(){
   float p = 31847;
	float alpha = 5;
	float beta = 26379;
	float message = 20543;
	float gamma = 20679;
	float delta = 11082;
	float alphaX = 	Math.pow(alpha,message)%p;
	float betaGamma = Math.pow(beta, gamma)%p;
	float gammaDelta = Math.pow(gamma, delta)%p;
	float val = (beta gamma*gamma delta)%p;
	if(val.equals(alphaX)) {
		System.out.println("Verified");
	} else {
		System.out.println("Not Verified");
	}
}

public void problem1PartB(double alpha, double beta, double n){
	double a=0.0;
	double m= Math.ceil(Math.sqrt(n));
	double x = Math.pow(alpha,m)% (n + 1);
   Map<Double, Double> L1 = new TreeMap<Double, Double>();
   Map<Double, Double> L2 = new TreeMap<Double, Double>();
	for(int i = 1; i < m; i++) {
		L1.put(i,(Math.pow(x, i - 1)%(n + 1)));
	}
   
   for(int i =1; i < m; i++) {
      double L2_aux = Math.pow(alpha, i-1)%(n + 1);
      [r, inverse , t] = extendedEuclid(L2 aux, (n + 1));
      L2.put = [i, Math.pow(beta*inverse, 1)% (n + 1);
   }
   
   for(int i = 0; i < m; i++){
      for(int j = 0; j <m;j++){
         Set<Double> L1Set = L1.keySet();
         Set<Double> L2Set = L2.keySet();
         Double L1Val = L1Set.remove();
         Double L2Val =L2Set.remove();
         if(L2.get(L2Val).equals(L1.get(L1Val)){
            a=(m*(L1.get(L1Val)-1)+(L2.get(L2Val)-1))%n;
            j = m;
            i = m;
         }
      }
   }
   System.out.println(a+"");
}

public void problem1PartC(double p, double alpha, double beta, double a, double message, double gamma, double delta){
   double k = 0.0;
   double aux = (message - a*gamma)% (p-1);
   if (gcd(delta, (p-1))==1){
       [r , inverse delta , t ] = extendedEuclid( delta , (p-1));
       k = ((aux*inverse_delta)% (p-1))
   } else {
      double d=gcd(delta, (p-1)); 
      double delta_prime = delta/d;
      double p_prime = (p-1)/d;
      double m_prime = aux/d;
      [r, inverse , t] = extendedEuclid(delta_prime , p_prime); 
      double k_prime = ((m_prime*inverse)% p_prime);
      for(int i = 0; i <d;i++){
         k = k_prime + i*p prime;
         double beta_aux = Math.pow(alpha , k)%p;
         if(beta_auz==gamma){
            i = d;
         }
      }
   }
   System.out.println(k+"");
   
}

public void problem2(double p, double alpha , double beta , double m1, double m2, 
   double gamma1, double delta1 , double gamma2, double delta2) {
	
	double a = 0.0;
	double k = 0.0;
	double delta = (delta2-alpha*alpha*delta1)% (p- 1);
	double m = (m2 -alpha*alpha*m1- 2*delta2)% (p 1);
	double d = gcd(p-1, delta);
   double p_prime, delta_prime, m_prime, k_prime, r,  inverse,  t, gamma1_aux, beta_aux;
	if ( d == 1.0 ) {
   	[ r , inverse , t ] = extendedEuclid( delta , (p-1)); 
       k = (m*inverse)% (p-1);
   } else {
      p_prime = (p-1)/d;
      delta_prime = delta/d; 
      m_prime = m/d;
      [ r , inverse , t ] = extendedEuclid( delta_prime , (p-1));
      k = (m_prime*inverse)% (p_prime-1);
   
      for(int i =0; i <d;i++){
         k = k_prime + i*p_prime;
         gamma1_aux = Math.pow( alpha , k)%p;
         if(gamma1_aux == gamma){
         i = d;
      }
   }
   d = gcd(gamma1, p-1);
   if(d==1){
      [ r , inverse , t ] = extendedEuclid( delta_prime , (p-1));
      a=(inverse*(m1-k*delta1))%(p-1);
      System.out.println(a+"");
   } else {
      p_prime = (p-1)/d;
      x_prime = (m1-k*delta1)/d; 
      gamma_prime = gamma1/d;
      [r, inverse , t] = extendedEuclid(gamma_prime, p_prime);
      a_prime = mod((x_prime*inverse), p_prime);
      for(int i = 0; i < d; i++){
         a = a_prime + i*p_prime;
         beta_aux = Math.pow(alpha , a)%p;
         if(beta_aux==beta){
            i=d;
            System.out.println(a+"");
         }
      }
   }
}
	
public static int gcd(int a, int b) {    
        if(a == 0 || b == 0) { 
        		return a+b;
        }
        
        return gcd(b,a%b);    
 } 

public Triple extendedEuclid(BigInteger a, BigInteger b) {
    if (b.equals(new BigInteger("0"))) {
        return new Triple(a, new BigInteger("1"), new BigInteger("0"));
    } else {
       Triple i = extendedEuclid(b, a.mod(b));
       return new Triple(i.getA(), i.getB(), (i.getC().divide(i.getB()).multiply(i.getC())));
    }
}
