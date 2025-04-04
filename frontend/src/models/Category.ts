export interface Category {
  id: string | number | null;
  name: string;
  description: string;
  parent?: Category | null;
}
