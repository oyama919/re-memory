export const Count_Up = "Count_Up"
let nextCount = 0;

export const countUpAction = () => ({
  type: Count_Up as typeof Count_Up,
  count: nextCount++,
});
