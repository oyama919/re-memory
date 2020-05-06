namespace Store {
    export interface CountUp {
        count: number;
      }
}

interface Store {
    countUp: Store.CountUp ;
}

export default Store
