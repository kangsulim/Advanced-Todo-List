export type Todo = {
  // id: string;
  id: number;
  text: string;
  completed: boolean;
  user: {
    name: string;
  }
};