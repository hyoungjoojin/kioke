interface Collection {
  id: string;
  name: string;
  description?: string;
  isDefault: boolean;
  journals: {
    id: string;
    title: string;
  }[];
}

export type { Collection };
