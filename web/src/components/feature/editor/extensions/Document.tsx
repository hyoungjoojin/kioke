import { Node } from '@tiptap/react';

export const Document = Node.create({
  name: 'doc',
  topNode: true,
  content: 'block+',
});
