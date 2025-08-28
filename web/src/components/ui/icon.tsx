import { cn } from '@/lib/utils';

export const enum IconName {
  PLUS = 'plus',
  X = 'x',
  USER = 'user',
  SIGN_OUT = 'sign-out',
  EDIT = 'edit',
  TRASH = 'trash',
  IMAGE = 'image',
  SEARCH = 'search',
  SETTINGS = 'settings',
  PAINT = 'paint',
  BELL = 'bell',
  DOTS = 'dots',
  DANGER = 'danger',
}

type IconSize = 'sm' | 'md' | 'lg' | number;

type IconProps = React.ComponentProps<'svg'> & {
  name: IconName;
  size?: IconSize;
  className?: string;
};

function getIconSize(size: IconSize): number {
  if (typeof size === 'number') {
    return size;
  }

  switch (size) {
    case 'sm':
      return 15;
    case 'md':
      return 24;
    case 'lg':
      return 28;
  }
}

export default function Icon({
  name,
  size = 'md',
  className,
  ...props
}: IconProps) {
  const width = getIconSize(size),
    height = width;

  return (
    <svg
      width={width}
      height={height}
      strokeLinecap='round'
      strokeLinejoin='round'
      className={cn('stroke-black', className)}
      {...props}
    >
      <use href={`/assets/icons/sprite.svg#${name}`} />
    </svg>
  );
}
