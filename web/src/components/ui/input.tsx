import type { IconName } from './icon';
import Icon from './icon';
import { cn } from '@/lib/utils';
import * as React from 'react';

function Input({
  className,
  type,
  icon,
  ...props
}: React.ComponentProps<'input'> & {
  icon?: IconName;
}) {
  return (
    <div className='flex items-center rounded-md border border-input bg-transparent text-base shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium file:text-foreground focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50 md:text-sm h-9 '>
      {icon && (
        <div className='mx-1'>
          <Icon name={icon} />
        </div>
      )}
      <input
        type={type}
        data-slot='input'
        className={cn(
          'flex w-full px-3 py-1',
          'placeholder:text-muted-foreground',
          className,
        )}
        {...props}
      />
    </div>
  );
}

export { Input };
