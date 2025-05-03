export enum Role {
  AUTHOR = 'AUTHOR',
  EDITOR = 'EDITOR',
  READER = 'READER',
}

export const Roles: {
  [key in Role]: {
    title: string;
  };
} = {
  [Role.AUTHOR]: {
    title: 'Author',
  },
  [Role.EDITOR]: {
    title: 'Editor',
  },
  [Role.READER]: {
    title: 'Reader',
  },
};
