interface Collection {
  id: string;
  name: string;
  description?: string;
  journals: {
    id: string;
    title: string;
  }[];
}

export type { Collection };
