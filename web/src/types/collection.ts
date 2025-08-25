export interface Collection {
  id: string;
  name: string;
  journals: {
    id: string;
    title: string;
  }[];
}
