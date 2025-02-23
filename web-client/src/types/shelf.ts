export interface Shelf {
  id: string;
  name: string;
  journals: {
    id: string;
    title: string;
  }[];
}
