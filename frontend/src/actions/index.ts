let nextId = 0;

export const countUp = () => ({
  type: "Count_Up",
  count: nextId++,
});