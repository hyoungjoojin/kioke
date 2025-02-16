export interface GetShelvesResponseBody {
  shelves: {
    id: string;
    name: string;
    journals: {
      id: string;
      title: string;
    }[];
  }[];
}
