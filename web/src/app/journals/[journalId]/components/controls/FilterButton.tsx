'use client';

import { Button } from '@/components/ui/button';
import { Filter } from 'lucide-react';

export default function FilterButton() {
  return (
    <Button variant='ghost'>
      <Filter />
    </Button>
  );
}
